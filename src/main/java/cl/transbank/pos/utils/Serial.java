package cl.transbank.pos.utils;

import cl.transbank.pos.exceptions.commonExceptions.TransbankException;
import cl.transbank.pos.responses.commonResponses.IntermediateResponse;
import com.fazecast.jSerialComm.SerialPort;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Log4j2
public class Serial {
    protected static final byte ACK = 0x06;
    protected static final int DEFAULT_TIMEOUT = 150000;
    protected static final int DEFAULT_BAUDRATE = 115200;
    private static final char STX = '\u0002';
    private static final char ETX = '\u0003';

    @Getter @Setter
    private int timeout = DEFAULT_TIMEOUT;
    protected String currentResponse;
    private SerialPort port;

    private Serial.OnIntermediateMessageReceivedListener onIntermediateMessageReceivedListener;

    public void setOnIntermediateMessageReceivedListener(OnIntermediateMessageReceivedListener listener) {
        onIntermediateMessageReceivedListener = listener;
    }

    private void setCurrentResponse(String response) {
        currentResponse = response;

        if(currentResponse.length() >= 1 && getFunctionCode().equals("0900")) {
            onIntermediateMessageReceivedListener.onReceived(new IntermediateResponse(currentResponse));
        }
    }

    public List<String> listPorts() {
        List<String> serialPorts = new ArrayList<>();
        SerialPort[] ports =  SerialPort.getCommPorts();

        for (SerialPort port : ports) {
            serialPorts.add(port.getSystemPortName());
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

    private boolean cantWrite() { return port == null || !port.isOpen(); }

    private String createCommand(String payload) {
        String fullCommand = STX+payload+ETX;
        return fullCommand + lrc(fullCommand);
    }

    private char lrc(String command) {
        char lrc = (char)0;

        for (int i = 1; i < command.length(); i++)
        {
            lrc ^= command.charAt(i);
        }
        return lrc;
    }

    public void write(String payload) throws TransbankException { write(payload, false); }

    public void write(String payload, boolean intermediateMessages) throws TransbankException {
        currentResponse = "";

        if(cantWrite())
            throw new TransbankException(String.format("Unable to send message to %s", port.getSystemPortName()));

        String command = createCommand(payload);
        byte[] hexCommand = command.getBytes();

        log.debug(String.format("Request [Hex]: %s", toHexString(hexCommand)));
        log.debug(String.format("Request [ASCII]: %s", command));

        port.writeBytes(hexCommand, hexCommand.length);

        if(!checkAck()) throw new TransbankException("NACK received, check the message sent to the POS");
        log.debug("Read ACK Ok");

        readResponse();

        if(intermediateMessages) {
            String responseCode = getResponseCode();
            while(CheckIntermediateMessage(responseCode)) {
                readResponse();
                responseCode = getResponseCode();
            }
            return;
        }
    }

    private void readResponse() throws TransbankException {
        waitResponse();

        int bytesAvailable = port.bytesAvailable();
        byte[] response = new byte[bytesAvailable];
        port.readBytes(response, bytesAvailable);
        setCurrentResponse(new String(response, StandardCharsets.UTF_8));

        log.debug(String.format("Response [Hex]: %s", toHexString(response)));
        log.debug(String.format("Response [ASCII]: %s", currentResponse));

        writeAck();
    }

    private boolean checkAck() throws TransbankException {
        byte[] response = new byte[1];

        waitResponse();

        port.readBytes(response, port.bytesAvailable());
        log.debug(String.format("Checking ACK [Hex]: %02X", response[0]));
        return response[0] == ACK;
    }

    private void waitResponse() throws TransbankException {
        final boolean[] isTimeoutCompleted = {false};
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() { isTimeoutCompleted[0] = true; }
        };

        timer.schedule(timerTask, timeout);

        while (port.bytesAvailable() <= 0 && !isTimeoutCompleted[0]) {}

        if(isTimeoutCompleted[0])
            throw new TransbankException("Read operation Timeout");
        timer.cancel();
    }

    private void writeAck() {
        byte[] ack = {ACK};
        log.debug(String.format("Send ACK [Hex]: %02X", ack[0]));
        port.writeBytes(ack, ack.length);
    }

    private String toHexString(byte[] data) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < data.length; i++) {
            sb.append(String.format("%02X%s", data[i], (i < data.length - 1) ? "-" : ""));
        }

        return sb.toString();
    }

    private String getResponseCode() {
        return currentResponse.substring(1, currentResponse.length()-2).split("\\|")[1];
    }

    private String getFunctionCode() {
        return currentResponse.substring(1, currentResponse.length()-2).split("\\|")[0];
    }

    private boolean CheckIntermediateMessage(String responseCode)
    {
        List<String> intermediateMsg = new ArrayList<>(
                Arrays.asList("78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89")
        );

        return intermediateMsg.contains(responseCode);
    }

    public interface OnIntermediateMessageReceivedListener {
        void onReceived(IntermediateResponse intermediateMessage);
    }
}
