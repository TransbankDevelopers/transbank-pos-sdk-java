package cl.transbank.pos.exceptions.integrado;

import cl.transbank.pos.exceptions.common.TransbankException;
import cl.transbank.pos.responses.integrado.TotalsResponse;

public class TransbankTotalsException extends TransbankException {
    private TotalsResponse totalsResponse;

    public TransbankTotalsException(String message, TotalsResponse response) {
        super(message);
        totalsResponse = response;
    }

    public TransbankTotalsException(String message, Throwable cause) {super(message, cause);}

    public TransbankTotalsException(String message) {super(message);}
}
