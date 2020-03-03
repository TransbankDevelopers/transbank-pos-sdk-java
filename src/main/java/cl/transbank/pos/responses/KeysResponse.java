package cl.transbank.pos.responses;

import cl.transbank.pos.utils.BaseResponse;
import org.apache.log4j.Logger;

public class KeysResponse {

    final static Logger logger = Logger.getLogger(TotalsResponse.class);

    private final int functionCode;
    private final int responseCode;
    private final long commerceCode;
    private final String terminalId;

    public int getFunctionCode() {
        return functionCode;
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
        this.functionCode = cresponse.getFunction();
        this.commerceCode = cresponse.getCommerceCode();
        this.terminalId = cresponse.getTerminalId();
    }

    public boolean isSuccessful() {
        return this.getResponseCode() == 0;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" + //close response extends this class. so we print the right class name
                "isSuccesful=" + isSuccessful() +
                "functionCode=" + functionCode +
                ", responseCode=" + responseCode +
                ", commerceCode=" + commerceCode +
                ", terminalId='" + terminalId + '\'' +
                " }\n";
    }
}
