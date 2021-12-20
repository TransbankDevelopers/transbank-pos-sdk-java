package cl.transbank.pos.exceptions.integradoExceptions;

import cl.transbank.pos.exceptions.commonExceptions.TransbankException;
import cl.transbank.pos.responses.integradoResponses.LastSaleResponse;

public class TransbankLastSaleException extends TransbankException {
    private LastSaleResponse saleResponse;

    public TransbankLastSaleException(String message, LastSaleResponse response) {
        super(message);
        saleResponse = response;
    }

    public TransbankLastSaleException(String message, Throwable cause) {super(message, cause);}

    public TransbankLastSaleException(String message) {super(message);}
}
