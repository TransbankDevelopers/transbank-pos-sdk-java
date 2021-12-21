package cl.transbank.pos.exceptions.common;

import cl.transbank.pos.responses.common.RefundResponse;

public class TransbankRefundException extends TransbankException {
    private RefundResponse refundResponse;

    public TransbankRefundException(String message, RefundResponse response) {
        super(message);
        refundResponse = response;
    }

    public TransbankRefundException(String message, Throwable cause) {super(message, cause);}

    public TransbankRefundException(String message) {super(message);}
}
