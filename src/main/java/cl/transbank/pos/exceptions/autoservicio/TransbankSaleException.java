package cl.transbank.pos.exceptions.autoservicio;

import cl.transbank.pos.exceptions.common.TransbankException;
import cl.transbank.pos.responses.autoservicio.SaleResponse;

public class TransbankSaleException extends TransbankException {
    private SaleResponse saleResponse;

    public TransbankSaleException(String message, SaleResponse response) {
        super(message);
        saleResponse = response;
    }

    public TransbankSaleException(String message, Throwable cause) {super(message, cause);}

    public TransbankSaleException(String message) {super(message);}
}
