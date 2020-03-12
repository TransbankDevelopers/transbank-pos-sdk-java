package cl.transbank.pos.exceptions;

public class TransbankException extends Exception{
    public TransbankException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransbankException(String message) {
        super(message);
    }
}
