package cl.transbank.pos.exceptions.autoservicio;

import cl.transbank.pos.exceptions.common.TransbankException;

public class TransbankLastSaleException extends TransbankException {

    public TransbankLastSaleException(String message, Throwable cause) {super(message, cause);}

    public TransbankLastSaleException(String message) {super(message);}
}
