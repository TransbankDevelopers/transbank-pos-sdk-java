package cl.transbank.pos.responses;

import cl.transbank.pos.utils.TotalsCResponse;
import org.apache.log4j.Logger;

public class TotalsResponse {

    final static Logger logger = Logger.getLogger(TotalsResponse.class);

    private final int txCount;
    private final int txTotal;
    private final int functionCode;
    private final int  responseCode;

    public TotalsResponse(TotalsCResponse cresponse) {
        logger.debug("constructor ctotals: " + cresponse);
        //we get everything at once so we don't keep making JNI calls later on.
        this.responseCode = cresponse.getResponseCode();
        this.txCount = cresponse.getTxCount();
        this.txTotal = cresponse.getTxTotal();
        this.functionCode = cresponse.getFunction();
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

    public int getFunctionCode() {
        return functionCode;
    }

    public int getResponseCode() {
        return responseCode;
    }

    @Override
    public String toString() {
        return "TotalsResponse{ " +
                "isSuccesful=" + isSuccessful() +
                ", txCount=" + txCount +
                ", txTotal=" + txTotal +
                ", functionCode=" + functionCode +
                ", responseCode=" + responseCode +
                " }\n";
    }
}
