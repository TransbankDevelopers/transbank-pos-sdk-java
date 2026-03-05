package cl.transbank.pos.exceptions.common;

/**
 * Exception thrown when a refund operation fails.
 */
public class TransbankRefundException extends TransbankException {

    public TransbankRefundException(String message, Throwable cause) {super(message, cause);}

    public TransbankRefundException(String message) {super(message);}
}
