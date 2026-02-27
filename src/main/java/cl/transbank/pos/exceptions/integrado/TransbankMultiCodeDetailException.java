package cl.transbank.pos.exceptions.integrado;

/**
 * Exception thrown when requesting the multicode sale details fails in POS Integrado.
 */
public class TransbankMultiCodeDetailException extends TransbankDetailException {

    public TransbankMultiCodeDetailException(String message, Throwable cause) {super(message, cause);}

    public TransbankMultiCodeDetailException(String message) {super(message);}
}
