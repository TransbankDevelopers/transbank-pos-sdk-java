package cl.transbank.pos.responses;

import cl.transbank.pos.utils.BaseResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KeysResponse {

    private static final Logger logger = LogManager.getLogger(KeysResponse.class);

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

    public KeysResponse( int fc, int rc, long cc, String ti) {
        this.functionCode = fc;
        this.responseCode = rc;
        this.commerceCode = cc;
        this.terminalId = ti;
    }

    public KeysResponse(BaseResponse cresponse) {
        logger.debug("constructor ctotals: " + cresponse);
        //we get everything at once so we don't keep making JNI calls later on.
        this.responseCode = cresponse.getResponseCode();
        this.functionCode = cresponse.getFunction();
        this.commerceCode = cresponse.getCommerceCode();
        this.terminalId = cresponse.getTerminalId();
    }

    public boolean isSuccessful() {
        return this.getResponseCode() == 0;
    }

    public String getResponseMessage() {
        return ResponseCodes.getMessage(this.getResponseCode());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" + //close response extends this class. so we print the right class name
                "isSuccesful=" + isSuccessful() +
                ", functionCode=" + functionCode +
                ", responseCode=" + responseCode +
                ", commerceCode=" + commerceCode +
                ", terminalId='" + terminalId + '\'' +
                " }\n";
    }
}
