package cl.transbank.pos.responses;

import cl.transbank.pos.utils.RefundCResponse;

public class RefundResponse implements Response {

    private final int functionCode;
    private final int responseCode;
    private final long commerceCode;
    private final String terminalId;
    private final String authorizationCode;
    private final int operationID;
    private final int initialized;

    public RefundResponse(RefundCResponse cresponse) {
        this.responseCode = cresponse.getResponseCode();
        this.functionCode = cresponse.getFunction();
        this.commerceCode = cresponse.getCommerceCode();
        this.terminalId = cresponse.getTerminalId();
        this.authorizationCode = cresponse.getAuthorizationCode();
        this.operationID = cresponse.getOperationID();
        this.initialized = cresponse.getInitilized();
    }

    @Override
    public boolean isSuccessful() {
        return responseCode == 0;
    }

    @Override
    public String getResponseMessage() {
        return ResponseCodes.getMessage(this.getResponseCode());
    }

    @Override
    public int getFunctionCode() {
        return functionCode;
    }

    @Override
    public int getResponseCode() {
        return responseCode;
    }

    public long getCommerceCode() {
        return commerceCode;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public int getOperationID() {
        return operationID;
    }

    public int getInitialized() {
        return initialized;
    }

    @Override
    public String toString() {
        return "RefundResponse{"
                + "isSuccesful=" + isSuccessful()
                + ", functionCode=" + functionCode
                + ", responseCode=" + responseCode
                + ", commerceCode=" + commerceCode
                + ", terminalId=" + terminalId
                + ", authorizationCode=" + authorizationCode
                + ", operationID=" + operationID
                + ", initialized=" + initialized
                + " }\n";
    }
}
