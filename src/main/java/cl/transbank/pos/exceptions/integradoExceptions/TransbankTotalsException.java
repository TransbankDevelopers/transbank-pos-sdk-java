package cl.transbank.pos.exceptions.integradoExceptions;

import cl.transbank.pos.exceptions.commonExceptions.TransbankException;
import cl.transbank.pos.responses.integradoResponses.TotalsResponse;

public class TransbankTotalsException extends TransbankException {
    private TotalsResponse totalsResponse;

    public TransbankTotalsException(String message, TotalsResponse response) {
        super(message);
        totalsResponse = response;
    }

    public TransbankTotalsException(String message, Throwable cause) {super(message, cause);}

    public TransbankTotalsException(String message) {super(message);}
}
