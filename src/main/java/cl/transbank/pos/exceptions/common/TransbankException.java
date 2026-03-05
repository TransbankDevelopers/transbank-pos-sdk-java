package cl.transbank.pos.exceptions.common;

/**
 * Base checked exception for SDK errors.
 * Used as the parent type for operation-specific exceptions and also for
 * generic communication/protocol failures during POS command execution.
 */
public class TransbankException extends Exception {

    public TransbankException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransbankException(String message) {
        super(message);
    }
}
