package cl.transbank.pos.responses.common;

import lombok.AccessLevel;
import lombok.Getter;

@Getter
/**
 * Parsed representation of intermediate POS status messages.
 * <p>
 * Wraps {@link BasicResponse} and exposes its common status fields
 * (function code, response code and response message).
 */
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
