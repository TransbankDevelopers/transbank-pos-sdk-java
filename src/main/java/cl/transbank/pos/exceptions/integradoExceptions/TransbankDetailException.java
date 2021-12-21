package cl.transbank.pos.exceptions.integradoExceptions;

import cl.transbank.pos.exceptions.commonExceptions.TransbankException;
import cl.transbank.pos.responses.integradoResponses.DetailResponse;

public class TransbankDetailException extends TransbankException {
    private DetailResponse detailResponse;

    public TransbankDetailException(String message, DetailResponse response) {
        super(message);
        detailResponse = response;
    }

    public TransbankDetailException(String message, Throwable cause) {super(message, cause);}

    public TransbankDetailException(String message) {super(message);}
}
