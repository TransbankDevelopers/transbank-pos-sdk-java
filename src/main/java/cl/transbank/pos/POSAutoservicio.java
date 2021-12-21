package cl.transbank.pos;

import cl.transbank.pos.exceptions.common.*;
import cl.transbank.pos.exceptions.autoservicio.*;
import cl.transbank.pos.responses.common.*;
import cl.transbank.pos.responses.autoservicio.*;
import cl.transbank.pos.utils.Serial;
import lombok.extern.log4j.Log4j2;

@Log4j2
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class POSAutoservicio extends Serial {
    public boolean poll() throws TransbankException {
        if(cantWrite()) {
            String exceptionMessage = String.format("Unable to Poll, can't write to port %s", port.getSystemPortName());
            throw new TransbankException(exceptionMessage);
        }

        try {
            String command = createCommand("0100");
            byte[] hexCommand = command.getBytes();

            log.debug(String.format("Request [Hex]: %s", toHexString(hexCommand)));
            log.debug(String.format("Request [ASCII]: %s", command));

            port.writeBytes(hexCommand, hexCommand.length);
            return checkAck();

        } catch (TransbankException e) {
            String exceptionMessage = String.format("Unable to send Poll command on port %s", port.getSystemPortName());
            throw new TransbankException(exceptionMessage);
        }
    }

    public LoadKeysResponse loadKeys() throws TransbankLoadKeysException {
        try {
            write("0800");
            LoadKeysResponse response = new LoadKeysResponse(currentResponse);
            log.debug(response.toString());
            return response;
        } catch (TransbankException e) {
            throw new TransbankLoadKeysException("Unable to execute load keys in pos", e);
        }
    }

    public boolean initialization() throws TransbankException {
        if(cantWrite()) {
            String exceptionMessage = String.format("Unable to initialization, can't write to port %s", port.getSystemPortName());
            throw new TransbankException(exceptionMessage);
        }

        try {
            String command = createCommand("0070");
            byte[] hexCommand = command.getBytes();

            log.debug(String.format("Request [Hex]: %s", toHexString(hexCommand)));
            log.debug(String.format("Request [ASCII]: %s", command));

            port.writeBytes(hexCommand, hexCommand.length);
            return checkAck();

        } catch (TransbankException e) {
            String exceptionMessage = String.format("Unable to send initialization command on port %s", port.getSystemPortName());
            throw new TransbankException(exceptionMessage);
        }
    }

    public InitializationResponse initializationResponse() throws TransbankInitializationResponseException {
        try {
            write("0080");
            InitializationResponse response = new InitializationResponse(currentResponse);
            log.debug(response.toString());
            return response;
        } catch (TransbankException e) {
            throw new TransbankInitializationResponseException("Unable to execute initialization response in pos", e);
        }
    }

    public SaleResponse sale(
            int amount,
            String ticket,
            boolean sendVoucher,
            boolean sendStatus
    ) throws TransbankSaleException {

        if (amount < 50) throw new TransbankSaleException("Amount must be greater than 50.");
        if (amount > 999999999) throw new TransbankSaleException("Amount must be less than 999999999.");
        if (ticket.trim().length() > 20) throw new TransbankSaleException("Ticket must be up to 20 in length");

        String command = String.format("0200|%s|%s|%s|%s", amount, ticket, sendVoucher ? 1 : 0, sendStatus ? 1 : 0);

        try {
            write(command, sendStatus);
            SaleResponse response = new SaleResponse(currentResponse);
            log.debug(response.toString());
            return response;
        } catch (TransbankException e) {
            throw new TransbankSaleException("Unable to execute sale on pos", e);
        }
    }

    public MultiCodeSaleResponse multiCodeSale(
            int amount, String ticket,
            long commerceCode,
            boolean sendVoucher,
            boolean sendStatus
    ) throws TransbankMultiCodeSaleException {

        if (amount < 50) throw new TransbankMultiCodeSaleException("Amount must be greater than 50.");
        if (amount > 999999999) throw new TransbankMultiCodeSaleException("Amount must be less than 999999999.");
        if (ticket.trim().length() > 20) throw new TransbankMultiCodeSaleException("Ticket must be up to 20 in length");

        String command = String.format("0270|%s|%s|%s|%s|%s", amount, ticket, sendVoucher ? 1 : 0, sendStatus ? 1 : 0, commerceCode);

        try {
            write(command, sendStatus);
            MultiCodeSaleResponse response = new MultiCodeSaleResponse(currentResponse);
            log.debug(response.toString());
            return response;
        } catch (TransbankException e) {
            throw new TransbankMultiCodeSaleException("Unable to execute sale on pos", e);
        }
    }

    public LastSaleResponse lastSale(boolean sendVoucher) throws TransbankLastSaleException {
        String command = String.format("0250|%s", sendVoucher ? 1 : 0);

        try {
            write(command);
            LastSaleResponse response = new LastSaleResponse(currentResponse);
            log.debug(response.toString());
            return response;
        } catch (TransbankException e) {
            throw new TransbankLastSaleException("Unable to recover last sale from pos", e);
        }
    }

    public RefundResponse refund() throws TransbankRefundException {
        try {
            write("1200");
            RefundResponse response = new RefundResponse(currentResponse);
            log.debug(response.toString());
            return response;
        } catch (TransbankException e) {
            throw new TransbankRefundException("Unable to make refund on pos", e);
        }
    }

    public CloseResponse close(boolean sendVoucher) throws TransbankCloseException {
        String command = String.format("0500|%s", sendVoucher ? 1 : 0);

        try {
            write(command);
            CloseResponse response = new CloseResponse(currentResponse);
            log.debug(response.toString());
            return response;
        } catch (TransbankException e) {
            throw new TransbankCloseException("Unable to execute close in pos", e);
        }
    }
}
