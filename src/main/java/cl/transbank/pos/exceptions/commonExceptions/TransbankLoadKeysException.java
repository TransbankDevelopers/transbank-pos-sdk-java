package cl.transbank.pos.exceptions.commonExceptions;

import cl.transbank.pos.responses.commonResponses.LoadKeysResponse;

public class TransbankLoadKeysException extends TransbankException{
    private LoadKeysResponse loadKeysResponse;

    public TransbankLoadKeysException(String message, LoadKeysResponse response) {
        super(message);
        loadKeysResponse = response;
    }

    public TransbankLoadKeysException(String message, Throwable cause) {super(message, cause);}

    public TransbankLoadKeysException(String message) {super(message);}
}
