package cl.transbank.pos.responses;

import cl.transbank.pos.utils.BaseResponse;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class KeysResponse {

    final static Logger logger = Logger.getLogger(KeysResponse.class);

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

    public KeysResponse( int functionCode, int responseCode, long commerceCode, String terminalId) {
        this.functionCode = functionCode;
        this.responseCode = responseCode;
        this.commerceCode = commerceCode;
        this.terminalId = terminalId;
    }

    public KeysResponse(BaseResponse cresponse) {
        BasicConfigurator.configure();
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
