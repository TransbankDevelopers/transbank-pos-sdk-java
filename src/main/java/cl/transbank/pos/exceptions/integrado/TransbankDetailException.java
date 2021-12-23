package cl.transbank.pos.exceptions.integrado;

import cl.transbank.pos.exceptions.common.TransbankException;

public class TransbankDetailException extends TransbankException {

    public TransbankDetailException(String message, Throwable cause) {super(message, cause);}

    public TransbankDetailException(String message) {super(message);}
}
