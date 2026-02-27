package cl.transbank.pos.exceptions.autoservicio;

/**
 * Exception thrown when a multicode sale fails in POS Autoservicio.
 */
public class TransbankMultiCodeSaleException extends TransbankSaleException {

    public TransbankMultiCodeSaleException(String message, Throwable cause) {super(message, cause);}

    public TransbankMultiCodeSaleException(String message) {super(message);}
}
