package cl.transbank.pos.exceptions.autoservicio;

import cl.transbank.pos.exceptions.common.TransbankException;

public class TransbankInitializationResponseException extends TransbankException {

    public TransbankInitializationResponseException(String message, Throwable cause) {super(message, cause);}

    public TransbankInitializationResponseException(String message) {super(message);}
}
