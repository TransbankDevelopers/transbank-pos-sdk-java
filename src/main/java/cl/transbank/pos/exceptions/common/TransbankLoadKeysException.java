package cl.transbank.pos.exceptions.common;

/**
 * Exception thrown when the keys retrieval fails.
 */
public class TransbankLoadKeysException extends TransbankException{

    public TransbankLoadKeysException(String message, Throwable cause) {super(message, cause);}

    public TransbankLoadKeysException(String message) {super(message);}
}
