package cl.transbank.pos.exceptions.autoservicio;

import cl.transbank.pos.exceptions.common.TransbankException;
import cl.transbank.pos.responses.autoservicio.LastSaleResponse;

public class TransbankLastSaleException extends TransbankException {
    private LastSaleResponse lastSaleResponse;

    public TransbankLastSaleException(String message, LastSaleResponse response) {
        super(message);
        lastSaleResponse = response;
    }

    public TransbankLastSaleException(String message, Throwable cause) {super(message, cause);}

    public TransbankLastSaleException(String message) {super(message);}
}
