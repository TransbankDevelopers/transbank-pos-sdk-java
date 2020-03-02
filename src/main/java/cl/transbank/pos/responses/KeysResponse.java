package cl.transbank.pos.responses;

import cl.transbank.pos.utils.BaseResponse;

public class KeysResponse extends BaseResponse {

    public BaseResponse response;
    public BaseResponse getResponse() {
        return response;
    }

    public KeysResponse(BaseResponse cresponse) {
        response = cresponse;
    }

    public boolean isSuccessful() {
        return response.getResponseCode() == 0;
    }

    @Override
    public String toString() {
        return "Function: " + this.getFunction() + "\n" +
                "Response: " + this.getResponse() + "\n" +
                "Success?: " + this.isSuccessful() + "\n" +
                "Commerce Code: " + this.getCommerceCode() + "\n" +
                "Terminal Id: " + this.getTerminalId();
    }
}
