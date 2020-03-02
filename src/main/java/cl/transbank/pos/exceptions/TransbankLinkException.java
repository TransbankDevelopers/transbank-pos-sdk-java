package cl.transbank.pos.exceptions;

public class TransbankLinkException extends TransbankException {
    public TransbankLinkException(String message, Throwable cause) {
        super(message, cause);
    }
    public TransbankLinkException(String message) {
        super(message);
    }
}
