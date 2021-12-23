package cl.transbank.pos.exceptions.integrado;

import cl.transbank.pos.exceptions.common.TransbankException;

public class TransbankCloseException extends TransbankException {

    public TransbankCloseException(String message, Throwable cause) {super(message, cause);}

    public TransbankCloseException(String message) {super(message);}
}
