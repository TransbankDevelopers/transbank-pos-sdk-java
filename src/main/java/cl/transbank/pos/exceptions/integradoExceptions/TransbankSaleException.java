package cl.transbank.pos.exceptions.integradoExceptions;

import cl.transbank.pos.exceptions.commonExceptions.TransbankException;
import cl.transbank.pos.responses.integradoResponses.SaleResponse;

public class TransbankSaleException extends TransbankException {
    private SaleResponse saleResponse;

    public TransbankSaleException(String message, SaleResponse response) {
        super(message);
        saleResponse = response;
    }

    public TransbankSaleException(String message, Throwable cause) {super(message, cause);}

    public TransbankSaleException(String message) {super(message);}
}
