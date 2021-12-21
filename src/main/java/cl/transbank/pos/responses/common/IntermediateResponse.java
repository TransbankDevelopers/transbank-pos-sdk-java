package cl.transbank.pos.responses.common;

import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class IntermediateResponse {

    @Getter(AccessLevel.NONE)
    private final BasicResponse basicResponse;

    private final int functionCode;
    private final int responseCode;
    private final String responseMessage;

    public IntermediateResponse(String response) {
        basicResponse = new BasicResponse(response);
        functionCode = basicResponse.getFunctionCode();
        responseCode = basicResponse.getResponseCode();
        responseMessage = basicResponse.getResponseMessage();
    }
}
