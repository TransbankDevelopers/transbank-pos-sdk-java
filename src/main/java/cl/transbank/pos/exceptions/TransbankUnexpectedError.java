package cl.transbank.pos.exceptions;

public class TransbankUnexpectedError extends Error {
    public TransbankUnexpectedError(String message, Throwable cause) {
        super(message, cause);
    }
}
