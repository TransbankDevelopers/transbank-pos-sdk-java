package cl.transbank.pos.responses;

import cl.transbank.pos.utils.BaseResponse;

public class CloseResponse extends KeysResponse {

    public CloseResponse(BaseResponse cresponse) {
        super(cresponse);
    }

    @Override
    public String getResponseMessage() {
        return ResponseCodes.getMessage(this.getResponseCode());
    }
}
