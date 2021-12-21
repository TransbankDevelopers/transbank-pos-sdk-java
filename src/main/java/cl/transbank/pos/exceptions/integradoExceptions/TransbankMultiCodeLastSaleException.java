package cl.transbank.pos.exceptions.integradoExceptions;

import cl.transbank.pos.responses.integradoResponses.MultiCodeLastSaleResponse;

public class TransbankMultiCodeLastSaleException extends TransbankLastSaleException {
    public TransbankMultiCodeLastSaleException(String message, MultiCodeLastSaleResponse response) {super(message, response);}

    public TransbankMultiCodeLastSaleException(String message, Throwable cause) {super(message, cause);}
}
