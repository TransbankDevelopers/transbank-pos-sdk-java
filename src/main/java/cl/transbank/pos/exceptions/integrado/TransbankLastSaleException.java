package cl.transbank.pos.exceptions.integrado;

import cl.transbank.pos.exceptions.common.TransbankException;

/**
 * Exception thrown when the last sale operation fails in POS Integrado.
 */
public class TransbankLastSaleException extends TransbankException {

    public TransbankLastSaleException(String message, Throwable cause) {super(message, cause);}

    public TransbankLastSaleException(String message) {super(message);}
}
