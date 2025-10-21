package cl.transbank.pos.utils;

import cl.transbank.pos.exceptions.common.TransbankException;
import cl.transbank.pos.responses.common.IntermediateResponse;
import com.fazecast.jSerialComm.SerialPort;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Log4j2
public class Serial {
    protected static final byte ACK = 0x06;
    protected static final byte NACK = 0x15;
    protected static final int MAX_NACK_ATTEMPTS = 2;
    public static final int DEFAULT_TIMEOUT = 150000;
    public static final int DEFAULT_BAUDRATE = 115200;
    private static final long NANOSECONDS_PER_MILLISECOND = 1_000_000L;
    private static final char STX = '\u0002';
    private static final char ETX = '\u0003';

    @Getter
    @Setter
    private int timeout = DEFAULT_TIMEOUT;
    protected String currentResponse;
    protected SerialPort port;
    protected List<String> saleDetailResponse;

    private Serial.OnIntermediateMessageReceivedListener onIntermediateMessageReceivedListener;

    private String fullResponse = "";
    private int sentNack = 0;

    public void setOnIntermediateMessageReceivedListener(OnIntermediateMessageReceivedListener listener) {
        onIntermediateMessageReceivedListener = listener;
    }

    private void setCurrentResponse(String response) {
        currentResponse = response;

        if (checkIntermediateMessage(currentResponse)
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
        currentResponse = "";
        checkCanWrite();
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

        if (intermediateMessages) {
            readMessage();
            boolean isIntermediateMessage = checkIntermediateMessage(currentResponse);
            while (isIntermediateMessage) {
                readMessage();
                isIntermediateMessage = checkIntermediateMessage(currentResponse);
            }
            return;
        }

        if (saleDetail) {
            saleDetailResponse = new ArrayList<>();
            String authorizationCode = "";
            while (!authorizationCode.trim().isEmpty() && !printOnPOS) {
                readMessage();
                try {
                    authorizationCode = getAuthorizationCode(currentResponse);
                    if (!authorizationCode.trim().isEmpty()) {
                        saleDetailResponse.add(currentResponse);
                    }
                } catch (IndexOutOfBoundsException e) {
                    authorizationCode = "";
                }
            }
            return;
        }

        readMessage();
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

            while (checkMissingEtx(fullResponse)) {
                sleepQuiet(50);

                if (port.bytesAvailable() <= 0) {
                    sendNack();
                } else {
                    fullResponse = fullResponse + readExisting();
                }
            }
        } while (!checkReceivedLrc(fullResponse));

        setCurrentResponse(fullResponse);
        log.debug(String.format("Response [Hex]: %s", toHexString(fullResponse.getBytes(StandardCharsets.ISO_8859_1))));
        log.debug(String.format("Response [ASCII]: %s", fullResponse));
        writeAck();
    }

    private String readExisting() throws TransbankException {
        long deadline = System.nanoTime() + (long) timeout * NANOSECONDS_PER_MILLISECOND;
        StringBuilder responseBuilder = new StringBuilder();
        byte[] buffer = new byte[1024];

        while (System.nanoTime() < deadline) {
            int availableBytes = port.bytesAvailable();
            if (availableBytes <= 0) {
                break;
            }

            int bytesToRead = Math.min(availableBytes, buffer.length);
            int bytesRead = port.readBytes(buffer, bytesToRead);

            if (bytesRead > 0) {
                String chunk = new String(buffer, 0, bytesRead, StandardCharsets.ISO_8859_1);
                responseBuilder.append(chunk);

                if (bytesRead < bytesToRead) {
                    break;
                }

                if (port.bytesAvailable() == 0) {
                    break;
                }
            }
        }

        if (responseBuilder.length() == 0 && System.nanoTime() >= deadline) {
            throw new TransbankException("Read operation Timeout");
        }

        return responseBuilder.toString();
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
        long deadline = System.nanoTime() + (long) timeout * NANOSECONDS_PER_MILLISECOND;

        while (System.nanoTime() < deadline && port.bytesAvailable() <= 0) {
            // wait for data
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
        sleepQuiet(50);
    }

    private boolean checkMissingEtx(String response) {
        if (response.isEmpty())
            return false;
        if (response.length() < 2)
            return true;
        return response.charAt(response.length() - 2) != ETX;
    }

    private boolean checkReceivedLrc(String response) {
        if (response.isEmpty())
            return false;
        if (checkIntermediateMessage(response))
            return true;
        char received = response.charAt(response.length() - 1);
        char calculated = calculateResponseLrc(response);
        return received == calculated;
    }

    private char calculateResponseLrc(String message) {
        String trimmed = message.substring(1, message.length() - 1);
        return calculateLrc(trimmed);
    }

    private char calculateLrc(String message) {
        char x = 0;
        for (int i = 0; i < message.length(); i++)
            x ^= message.charAt(i);
        return x;
    }

    private void sleepQuiet(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
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

    private String getFunctionCode(String response) {
        return response.split("\\|", -1)[0];
    }

    private String getAuthorizationCode(String response) {
        String[] parts = response.split("\\|", -1);
        return parts.length > 5 ? parts[5] : "";
    }

    private boolean checkIntermediateMessage(String response) {
        if (response.length() >= 1) {
            String payload = response.substring(1, response.length() - 2);
            return getFunctionCode(payload).equals("0900");
        }

        return false;
    }

    public interface OnIntermediateMessageReceivedListener {
        void onReceived(IntermediateResponse intermediateMessage);
    }

    public boolean isPortOpen() {
        return port.isOpen();
    }
}
