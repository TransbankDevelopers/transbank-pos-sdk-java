package cl.transbank.pos.exceptions.autoservicio;

import cl.transbank.pos.exceptions.common.TransbankException;

/**
 * Exception thrown when the intialization fails in POS Autoservicio.
 */
public class TransbankInitializationResponseException extends TransbankException {

    public TransbankInitializationResponseException(String message, Throwable cause) {super(message, cause);}

    public TransbankInitializationResponseException(String message) {super(message);}
}
