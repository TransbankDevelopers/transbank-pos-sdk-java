package cl.transbank.pos.exceptions.autoservicio;

import cl.transbank.pos.exceptions.common.TransbankException;
import cl.transbank.pos.responses.autoservicio.InitializationResponse;

public class TransbankInitializationResponseException extends TransbankException {
    private InitializationResponse initializationResponse;

    public TransbankInitializationResponseException(String message, InitializationResponse response) {
        super(message);
        initializationResponse = response;
    }

    public TransbankInitializationResponseException(String message, Throwable cause) {super(message, cause);}

    public TransbankInitializationResponseException(String message) {super(message);}
}
