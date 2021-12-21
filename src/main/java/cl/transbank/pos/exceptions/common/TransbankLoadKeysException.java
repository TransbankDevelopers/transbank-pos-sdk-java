package cl.transbank.pos.exceptions.common;

import cl.transbank.pos.responses.common.LoadKeysResponse;

public class TransbankLoadKeysException extends TransbankException{
    private LoadKeysResponse loadKeysResponse;

    public TransbankLoadKeysException(String message, LoadKeysResponse response) {
        super(message);
        loadKeysResponse = response;
    }

    public TransbankLoadKeysException(String message, Throwable cause) {super(message, cause);}

    public TransbankLoadKeysException(String message) {super(message);}
}
