package cl.transbank.pos.exceptions.integrado;

import cl.transbank.pos.exceptions.common.TransbankException;

/**
 * Exception thrown when requesting transaction totals fails in POS Integrado.
 */
public class TransbankTotalsException extends TransbankException {

    public TransbankTotalsException(String message, Throwable cause) {super(message, cause);}

    public TransbankTotalsException(String message) {super(message);}
}
