package cl.transbank.pos.exceptions.autoservicio;

import cl.transbank.pos.exceptions.common.TransbankException;
import cl.transbank.pos.responses.autoservicio.CloseResponse;

public class TransbankCloseException extends TransbankException {
    private CloseResponse closeResponse;

    public TransbankCloseException(String message, CloseResponse response) {
        super(message);
        closeResponse = response;
    }

    public TransbankCloseException(String message, Throwable cause) {super(message, cause);}

    public TransbankCloseException(String message) {super(message);}
}
