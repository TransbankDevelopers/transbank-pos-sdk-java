package cl.transbank.pos.responses;

import cl.transbank.pos.utils.BaseResponse;
import cl.transbank.pos.utils.TotalsCResponse;
import org.apache.log4j.Logger;

public class KeysResponse {

    final static Logger logger = Logger.getLogger(TotalsResponse.class);

    private final int function;
    private final int responseCode;
    private final long commerceCode;
    private final String terminalId;

    public int getFunction() {
        return function;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public long getCommerceCode() {
        return commerceCode;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public KeysResponse(BaseResponse cresponse) {
        logger.debug("constructos ctotals: " + cresponse);
        //we get everything at once so we don't keep making JNI calls later on.
        this.responseCode = cresponse.getResponseCode();
        this.function = cresponse.getFunction();
        this.commerceCode = cresponse.getCommerceCode();
        this.terminalId = cresponse.getTerminalId();
    }

    public boolean isSuccessful() {
        return this.getResponseCode() == 0;
    }

    @Override
    public String toString() {
        return "Function: " + this.getFunction() + "\n" +
                "Response: " + this.getResponseCode() + "\n" +
                "Success?: " + this.isSuccessful() + "\n" +
                "Commerce Code: " + this.getCommerceCode() + "\n" +
                "Terminal Id: " + this.getTerminalId();
    }
}
