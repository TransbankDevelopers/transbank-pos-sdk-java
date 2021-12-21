package cl.transbank.pos.exceptions.integrado;

import cl.transbank.pos.responses.integrado.MultiCodeSaleResponse;

public class TransbankMultiCodeSaleException extends TransbankSaleException {
    MultiCodeSaleResponse multiCodeSaleResponse;

    public TransbankMultiCodeSaleException(String message, MultiCodeSaleResponse response) {
        super(message);
        multiCodeSaleResponse = response;
    }

    public TransbankMultiCodeSaleException(String message, Throwable cause) {super(message, cause);}

    public TransbankMultiCodeSaleException(String message) {super(message);}
}
