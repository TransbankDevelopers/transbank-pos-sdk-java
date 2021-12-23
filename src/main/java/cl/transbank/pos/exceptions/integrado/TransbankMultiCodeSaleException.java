package cl.transbank.pos.exceptions.integrado;

import cl.transbank.pos.responses.integrado.MultiCodeSaleResponse;

public class TransbankMultiCodeSaleException extends TransbankSaleException {

    public TransbankMultiCodeSaleException(String message, Throwable cause) {super(message, cause);}

    public TransbankMultiCodeSaleException(String message) {super(message);}
}
