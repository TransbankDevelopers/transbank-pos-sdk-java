package cl.transbank.pos;

import cl.transbank.pos.exceptions.commonExceptions.TransbankException;
import cl.transbank.pos.exceptions.commonExceptions.TransbankLoadKeysException;
import cl.transbank.pos.exceptions.integradoExceptions.TransbankSaleException;
import cl.transbank.pos.responses.commonResponses.LoadKeysResponse;
import cl.transbank.pos.responses.integradoResponses.SaleResponse;
import cl.transbank.pos.utils.Serial;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class POSIntegrado extends Serial {
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
}
