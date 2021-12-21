package cl.transbank.pos.exceptions.integrado;

import cl.transbank.pos.exceptions.common.TransbankException;
import cl.transbank.pos.responses.integrado.DetailResponse;

public class TransbankDetailException extends TransbankException {
    private DetailResponse detailResponse;

    public TransbankDetailException(String message, DetailResponse response) {
        super(message);
        detailResponse = response;
    }

    public TransbankDetailException(String message, Throwable cause) {super(message, cause);}

    public TransbankDetailException(String message) {super(message);}
}
