package cl.transbank.pos.exceptions.integradoExceptions;

import cl.transbank.pos.responses.integradoResponses.MultiCodeSaleResponse;

public class TransbankMultiCodeSaleException extends TransbankSaleException {
    MultiCodeSaleResponse multiCodeSaleResponse;

    public TransbankMultiCodeSaleException(String message, MultiCodeSaleResponse response) {
        super(message);
        multiCodeSaleResponse = response;
    }

    public TransbankMultiCodeSaleException(String message, Throwable cause) {super(message, cause);}

    public TransbankMultiCodeSaleException(String message) {super(message);}
}
