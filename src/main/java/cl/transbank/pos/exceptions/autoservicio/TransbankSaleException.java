package cl.transbank.pos.exceptions.autoservicio;

import cl.transbank.pos.exceptions.common.TransbankException;

public class TransbankSaleException extends TransbankException {

    public TransbankSaleException(String message, Throwable cause) {super(message, cause);}

    public TransbankSaleException(String message) {super(message);}
}
