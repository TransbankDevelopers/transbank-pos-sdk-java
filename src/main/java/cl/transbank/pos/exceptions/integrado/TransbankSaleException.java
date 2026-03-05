package cl.transbank.pos.exceptions.integrado;

import cl.transbank.pos.exceptions.common.TransbankException;

/**
 * Exception thrown when a sale operation fails in POS Integrado.
 */
public class TransbankSaleException extends TransbankException {

    public TransbankSaleException(String message, Throwable cause) {super(message, cause);}

    public TransbankSaleException(String message) {super(message);}
}
