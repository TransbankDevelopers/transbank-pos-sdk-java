package cl.transbank.pos.exceptions;

public class TransbankUnexpectedError extends Error {

    private static final long serialVersionUID = 1L;

    public TransbankUnexpectedError(String message) {
        super(message);
    }

    public TransbankUnexpectedError(String message, Throwable cause) {
        super(message, cause);
    }
}
