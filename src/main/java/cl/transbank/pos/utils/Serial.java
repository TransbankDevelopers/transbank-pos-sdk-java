package cl.transbank.pos.utils;

import cl.transbank.pos.exceptions.common.TransbankException;
import cl.transbank.pos.responses.common.IntermediateResponse;
import com.fazecast.jSerialComm.SerialPort;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.util.*;

/**
 * Base class for POS serial communication.
 * Handles port management, command framing (STX/ETX/LRC), ACK/NACK flow,
 * response reading with timeout, and intermediate message callbacks.
 */
@Log4j2
public class Serial {
    protected static final byte ACK = 0x06;
    protected static final byte NACK = 0x15;
    protected static final int MAX_NACK_ATTEMPTS = 2;
    protected static final int CONSECUTIVE_EMPTY_AUTHCODE_LIMIT = 2;
    public static final int DEFAULT_TIMEOUT = 150000;
    public static final int DEFAULT_BAUDRATE = 115200;
    private static final char STX = '\u0002';
    private static final char ETX = '\u0003';

    @Getter
    @Setter
    private int timeout = DEFAULT_TIMEOUT;
    protected String currentResponse;
    protected SerialPort port;
    protected List<String> saleDetailResponse;
    protected SerialMessageUtils.PosModel lrcPosModel = SerialMessageUtils.PosModel.INTEGRADO;

    private Serial.OnIntermediateMessageReceivedListener onIntermediateMessageReceivedListener;

    private String fullResponse = "";
    private int sentNack = 0;

    public void setOnIntermediateMessageReceivedListener(OnIntermediateMessageReceivedListener listener) {
        onIntermediateMessageReceivedListener = listener;
    }

    protected void setLrcPosModel(SerialMessageUtils.PosModel posModel) {
        lrcPosModel = posModel;
    }

    private long currentTimeMillis() {
        Clock clock = Clock.systemUTC();
        return clock.millis();
    }

    private void setCurrentResponse(String response) {
        currentResponse = response;

        if (SerialMessageUtils.checkIntermediateMessage(currentResponse)
                && onIntermediateMessageReceivedListener != null) {
            onIntermediateMessageReceivedListener.onReceived(new IntermediateResponse(currentResponse));
        }
    }

    public List<String> listPorts() {
        List<String> serialPorts = new ArrayList<>();
        SerialPort[] ports = SerialPort.getCommPorts();

        for (SerialPort serialPort : ports) {
            serialPorts.add(serialPort.getSystemPortName());
        }

        return serialPorts;
    }

    public boolean openPort(String portName) {
        log.debug(String.format("Opening port %s", portName));
        return openPort(portName, DEFAULT_BAUDRATE);
    }

    public boolean openPort(String portName, int baudRate) {
        port = SerialPort.getCommPort(portName);
        port.setBaudRate(baudRate);
        return port.openPort();
    }

    public boolean closePort() {
        if (port == null) {
            return false;
        }
        return port.closePort();
    }

    protected void checkCanWrite() throws TransbankException {
        if (port == null || !port.isOpen()) {
            throw new TransbankException("Can't write to port, the port is null or not open");
        }
    }

    protected String createCommand(String payload) {
        String fullCommand = STX + payload + ETX;
        return fullCommand + lrc(fullCommand);
    }

    private char lrc(String command) {
        char lrc = (char) 0;

        for (int i = 1; i < command.length(); i++) {
            lrc ^= command.charAt(i);
        }
        return lrc;
    }

    protected void write(String payload) throws TransbankException, IOException {
        write(payload, false);
    }

    protected void write(String payload, boolean intermediateMessages)
            throws TransbankException, IOException {
        write(payload, intermediateMessages, false, false);
    }

    protected void write(String payload, boolean intermediateMessages, boolean saleDetail, boolean printOnPOS)
            throws TransbankException, IOException {
        log.debug("write() start: payload=" + payload);
        currentResponse = "";
        checkCanWrite();
        sendCommandAndValidateAck(payload);

        if (intermediateMessages) {
            consumeIntermediateMessages();
            return;
        }

        if (saleDetail) {
            handleSaleDetail(printOnPOS);
            return;
        }

        log.debug("write() -> about to readMessage()");
        readMessage();
    }

    private void sendCommandAndValidateAck(String payload) throws TransbankException, IOException {
        String command = createCommand(payload);
        byte[] hexCommand = command.getBytes(StandardCharsets.ISO_8859_1);
        log.debug(String.format("Request [Hex]: %s", toHexString(hexCommand)));
        log.debug(String.format("Request [ASCII]: %s", command));
        port.flushIOBuffers();
        port.writeBytes(hexCommand, hexCommand.length);

        if (!checkAck()) {
            throw new TransbankException("NACK received, check the message sent to the POS");
        }
        log.debug("Read ACK Ok");
    }

    private void consumeIntermediateMessages() throws TransbankException {
        readMessage();
        while (SerialMessageUtils.checkIntermediateMessage(currentResponse)) {
            readMessage();
        }
    }

    private void handleSaleDetail(boolean printOnPOS) throws TransbankException {
        saleDetailResponse = new ArrayList<>();
        if (printOnPOS) {
            return;
        }

        int consecutiveEmptyAuthCodes = 0;

        while (consecutiveEmptyAuthCodes < CONSECUTIVE_EMPTY_AUTHCODE_LIMIT) {
            readMessage();
            try {
                String authorizationCode = SerialMessageUtils.getAuthorizationCode(currentResponse);
                if (authorizationCode != null && !authorizationCode.trim().isEmpty()) {
                    saleDetailResponse.add(currentResponse);
                    consecutiveEmptyAuthCodes = 0;
                } else {
                    consecutiveEmptyAuthCodes++;
                }
            } catch (IndexOutOfBoundsException e) {
                consecutiveEmptyAuthCodes++;
            }
        }
    }

    private void readMessage() throws TransbankException {
        waitResponse();

        fullResponse = "";
        sentNack = 0;

        do {
            if (!fullResponse.isEmpty()) {
                sendNack();
            }

            fullResponse = readExisting();

            while (SerialMessageUtils.checkMissingEtx(fullResponse)) {
                waitQuiet(50);

                if (port.bytesAvailable() <= 0) {
                    sendNack();
                } else {
                    fullResponse = fullResponse + readExisting();
                }
            }
        } while (!SerialMessageUtils.checkReceivedLrc(fullResponse, lrcPosModel));

        setCurrentResponse(fullResponse);
        log.debug(String.format("Response [Hex]: %s", toHexString(fullResponse.getBytes(StandardCharsets.ISO_8859_1))));
        log.debug(String.format("Response [ASCII]: %s", fullResponse));
        writeAck();
    }

    private String readExisting() throws TransbankException {
        long deadline = currentTimeMillis() + timeout;
        ByteArrayOutputStream responseBuffer = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        boolean stopReading = false;

        while (currentTimeMillis() < deadline && !stopReading) {
            int availableBytes = port.bytesAvailable();
            if (availableBytes <= 0) {
                stopReading = true;
            } else {
                int bytesToRead = Math.min(availableBytes, buffer.length);
                int bytesRead = port.readBytes(buffer, bytesToRead);

                if (bytesRead > 0) {
                    responseBuffer.write(buffer, 0, bytesRead);
                    stopReading = bytesRead < bytesToRead || port.bytesAvailable() == 0;
                }
            }
        }

        if (responseBuffer.size() == 0 && currentTimeMillis() >= deadline) {
            throw new TransbankException("Read operation Timeout");
        }

        return new String(responseBuffer.toByteArray(), StandardCharsets.ISO_8859_1);
    }

    protected boolean checkAck() throws TransbankException {
        byte[] response = new byte[1];

        waitResponse();
        int n = port.readBytes(response, Math.min(1, port.bytesAvailable()));

        if (n <= 0) {
            throw new TransbankException("Read operation Timeout waiting ACK");
        }

        log.debug(String.format("Checking ACK [Hex]: %02X", response[0]));
        return response[0] == ACK;
    }

    private void waitResponse() throws TransbankException {
        long deadline = currentTimeMillis() + timeout;

        while (currentTimeMillis() < deadline && port.bytesAvailable() <= 0) {
            waitQuiet(1);
        }

        if (port.bytesAvailable() <= 0) {
            port.flushIOBuffers();
            throw new TransbankException("Read operation Timeout");
        }
    }

    private void sendNack() throws TransbankException {
        if (sentNack >= MAX_NACK_ATTEMPTS) {
            fullResponse = "";
            throw new TransbankException("Invalid message received");
        }
        byte[] nack = { NACK };
        port.writeBytes(nack, nack.length);
        sentNack++;
        fullResponse = "";
        waitQuiet(50);
    }

    private void waitQuiet(long ms) {
        Object waitMonitor = new Object();
        synchronized (waitMonitor) {
            long deadline = currentTimeMillis() + ms;
            long remaining = ms;
            try {
                while (remaining > 0) {
                    waitMonitor.wait(remaining);
                    remaining = deadline - currentTimeMillis();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void writeAck() {
        byte[] ack = { ACK };
        log.debug(String.format("Send ACK [Hex]: %02X", ack[0]));
        port.writeBytes(ack, ack.length);
    }

    protected String toHexString(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            sb.append(String.format("%02X%s", data[i], (i < data.length - 1) ? "-" : ""));
        }
        return sb.toString();
    }

    /**
     * Callback for intermediate POS responses (function code {@code 0900}).
     */
    public interface OnIntermediateMessageReceivedListener {
        void onReceived(IntermediateResponse intermediateMessage);
    }

    public boolean isPortOpen() {
        if (port == null) {
            return false;
        }
        return port.isOpen();
    }
}
