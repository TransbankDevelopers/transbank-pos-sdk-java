package cl.transbank.pos.exceptions.integrado;

/**
 * Exception thrown when the multicode last sale operation fails in POS Integrado.
 */
public class TransbankMultiCodeLastSaleException extends TransbankLastSaleException {

    public TransbankMultiCodeLastSaleException(String message, Throwable cause) {super(message, cause);}

    public TransbankMultiCodeLastSaleException(String message) {super(message);}
}
