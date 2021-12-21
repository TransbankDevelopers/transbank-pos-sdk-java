package cl.transbank.pos;

import cl.transbank.pos.exceptions.common.TransbankException;
import cl.transbank.pos.exceptions.common.TransbankLoadKeysException;
import cl.transbank.pos.exceptions.common.TransbankRefundException;
import cl.transbank.pos.exceptions.integrado.*;
import cl.transbank.pos.responses.common.LoadKeysResponse;
import cl.transbank.pos.responses.common.RefundResponse;
import cl.transbank.pos.responses.integrado.*;
import cl.transbank.pos.utils.Serial;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class POSIntegrado extends Serial {
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

    public boolean setNormalMode() throws TransbankException {
        if(cantWrite()) {
            String exceptionMessage = String.format("Unable to set Normal Mode, can't write to port %s", port.getSystemPortName());
            throw new TransbankException(exceptionMessage);
        }

        try {
            String command = createCommand("0300");
            byte[] hexCommand = command.getBytes();

            log.debug(String.format("Request [Hex]: %s", toHexString(hexCommand)));
            log.debug(String.format("Request [ASCII]: %s", command));

            port.writeBytes(hexCommand, hexCommand.length);
            return checkAck();

        } catch (TransbankException e) {
            String exceptionMessage = String.format("Unable to send Normal Mode command on port %s", port.getSystemPortName());
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

    public SaleResponse sale(int amount, String ticket, boolean sendStatus) throws TransbankSaleException {
        if (amount < 50) throw new TransbankSaleException("Amount must be greater than 50.");
        if (amount > 999999999) throw new TransbankSaleException("Amount must be less than 999999999.");
        if (ticket.trim().length() > 6) throw new TransbankSaleException("Ticket must be up to 6 in length");

        String command = String.format("0200|%s|%s|||%s|", amount, ticket, sendStatus ? 1 : 0);

        try {
            write(command, sendStatus);
            SaleResponse response = new SaleResponse(currentResponse);
            log.debug(response.toString());
            return response;
        } catch (TransbankException e) {
            throw new TransbankSaleException("Unable to execute sale on pos", e);
        }
    }

    public MultiCodeSaleResponse multiCodeSale(int amount, String ticket, long commerceCode, boolean sendStatus) throws TransbankMultiCodeSaleException {
        if (amount < 50) throw new TransbankMultiCodeSaleException("Amount must be greater than 50.");
        if (amount > 999999999) throw new TransbankMultiCodeSaleException("Amount must be less than 999999999.");
        if (ticket.trim().length() > 6) throw new TransbankMultiCodeSaleException("Ticket must be up to 6 in length");

        String command = String.format("0270|%s|%s|||%s|%s|", amount, ticket, sendStatus ? 1 : 0, commerceCode);

        try {
            write(command, sendStatus);
            MultiCodeSaleResponse response = new MultiCodeSaleResponse(currentResponse);
            log.debug(response.toString());
            return response;
        } catch (TransbankException e) {
            throw new TransbankMultiCodeSaleException("Unable to execute sale on pos", e);
        }
    }

    public LastSaleResponse lastSale() throws TransbankLastSaleException {
        try {
            write("0250|");
            LastSaleResponse response = new LastSaleResponse(currentResponse);
            log.debug(response.toString());
            return response;
        } catch (TransbankException e) {
            throw new TransbankLastSaleException("Unable to recover last sale from pos", e);
        }
    }

    public MultiCodeLastSaleResponse multiCodeLastSale() throws TransbankMultiCodeLastSaleException {
        try {
            write("0280|");
            MultiCodeLastSaleResponse response = new MultiCodeLastSaleResponse(currentResponse);
            log.debug(response.toString());
            return response;
        } catch (TransbankException e) {
            throw new TransbankMultiCodeLastSaleException("Unable to recover last sale from pos", e);
        }
    }

    public RefundResponse Refund(int operationId) throws TransbankRefundException {
        String command = String.format("1200|%s|", operationId);

        try {
            write(command);
            RefundResponse response = new RefundResponse(currentResponse);
            log.debug(response.toString());
            return response;
        } catch (TransbankException e) {
            throw new TransbankRefundException("Unable to make refund on pos", e);
        }
    }

    public TotalsResponse totals() throws TransbankTotalsException {
        try {
            write("0700||");
            TotalsResponse response = new TotalsResponse(currentResponse);
            log.debug(response.toString());
            return response;
        } catch (TransbankException e) {
            throw new TransbankTotalsException("Unable to get totals from pos", e);
        }
    }

    public List<DetailResponse> details(boolean printOnPOS) throws TransbankDetailException {
        String command = String.format("0260|%s|", printOnPOS ? 0 : 1);
        List<DetailResponse> details = new ArrayList<>();

        try {
            write(command, false, true, printOnPOS);
            for (String sale : saleDetailResponse) {
                DetailResponse saleDetail = new DetailResponse(sale);
                details.add(saleDetail);
                log.debug(saleDetail.toString());
            }
            return details;
        } catch (TransbankException e) {
            throw new TransbankDetailException("Unable to request sale detail on pos", e);
        }
    }

    public List<MultiCodeDetailResponse> multiCodeDetails(boolean printOnPOS) throws TransbankMultiCodeDetailException {
        String command = String.format("0260|%s|", printOnPOS ? 0 : 1);
        List<MultiCodeDetailResponse> details = new ArrayList<>();

        try {
            write(command, false, true, printOnPOS);
            for (String sale : saleDetailResponse) {
                MultiCodeDetailResponse saleDetail = new MultiCodeDetailResponse(sale);
                details.add(saleDetail);
                log.debug(saleDetail.toString());
            }
            return details;
        } catch (TransbankException e) {
            throw new TransbankMultiCodeDetailException("Unable to request sale detail on pos", e);
        }
    }

    public CloseResponse close() throws TransbankCloseException {
        try {
            write("0500||");
            CloseResponse response = new CloseResponse(currentResponse);
            log.debug(response.toString());
            return response;
        } catch (TransbankException e) {
            throw new TransbankCloseException("Unable to execute close in pos", e);
        }
    }
}
