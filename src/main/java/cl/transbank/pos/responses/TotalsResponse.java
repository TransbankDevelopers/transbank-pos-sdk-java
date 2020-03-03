package cl.transbank.pos.responses;

import cl.transbank.pos.utils.TotalsCResponse;
import org.apache.log4j.Logger;

public class TotalsResponse {

    final static Logger logger = Logger.getLogger(TotalsResponse.class);

    private final int txCount;
    private final int txTotal;
    private final int function;
    private final int  responseCode;

    public TotalsResponse(TotalsCResponse cresponse) {
        logger.debug("constructor ctotals: " + cresponse);
        //we get everything at once so we don't keep making JNI calls later on.
        this.responseCode = cresponse.getResponseCode();
        this.txCount = cresponse.getTxCount();
        this.txTotal = cresponse.getTxTotal();
        this.function = cresponse.getFunction();
    }

    @Override
    public String toString() {
        return "Function: " + this.getFunction() + "\n" +
                "Response: " + this.getResponseCode() + "\n" +
                "Success?: " + this.isSuccessful() + "\n" +
                "Message?: " + this.getResponseMessage() + "\n" +
                "TX Count: " + this.getTxCount() + "\n" +
                "TX Total: " + this.getTxTotal();
    }

    public boolean isSuccessful() {
        return this.getResponseCode() == 0;
    }

    private String getResponseMessage() {
        return ResponseCodes.getMessage(this.getResponseCode());
    }

    public int getTxCount() {
        return txCount;
    }

    public int getTxTotal() {
        return txTotal;
    }

    public int getFunction() {
        return function;
    }

    public int getResponseCode() {
        return responseCode;
    }

}
