package cl.transbank.pos.exceptions.integrado;

import cl.transbank.pos.exceptions.common.TransbankException;
import cl.transbank.pos.responses.integrado.SaleResponse;

public class TransbankSaleException extends TransbankException {
    private SaleResponse saleResponse;

    public TransbankSaleException(String message, SaleResponse response) {
        super(message);
        saleResponse = response;
    }

    public TransbankSaleException(String message, Throwable cause) {super(message, cause);}

    public TransbankSaleException(String message) {super(message);}
}
