package cl.transbank.pos.exceptions.integrado;

/**
 * Exception thrown when a multicode sale operation fails in POS Integrado.
 */
public class TransbankMultiCodeSaleException extends TransbankSaleException {

    public TransbankMultiCodeSaleException(String message, Throwable cause) {super(message, cause);}

    public TransbankMultiCodeSaleException(String message) {super(message);}
}
