package cl.transbank.pos.exceptions.commonExceptions;

import cl.transbank.pos.responses.commonResponses.RefundResponse;

public class TransbankRefundException extends TransbankException {
    private RefundResponse refundResponse;

    public TransbankRefundException(String message, RefundResponse response) {
        super(message);
        refundResponse = response;
    }

    public TransbankRefundException(String message, Throwable cause) {super(message, cause);}

    public TransbankRefundException(String message) {super(message);}
}
