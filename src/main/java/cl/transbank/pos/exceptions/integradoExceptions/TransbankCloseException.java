package cl.transbank.pos.exceptions.integradoExceptions;

import cl.transbank.pos.exceptions.commonExceptions.TransbankException;
import cl.transbank.pos.responses.integradoResponses.CloseResponse;

public class TransbankCloseException extends TransbankException {
    private CloseResponse closeResponse;

    public TransbankCloseException(String message, CloseResponse response) {
        super(message);
        closeResponse = response;
    }

    public TransbankCloseException(String message, Throwable cause) {super(message, cause);}

    public TransbankCloseException(String message) {super(message);}
}
