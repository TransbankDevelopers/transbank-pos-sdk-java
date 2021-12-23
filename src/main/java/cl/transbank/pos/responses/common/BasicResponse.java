package cl.transbank.pos.responses.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import cl.transbank.pos.utils.ResponseCodes;
import lombok.AccessLevel;
import lombok.Getter;

import static cl.transbank.pos.utils.ParameterParser.parseIntParameter;

@Getter
public class BasicResponse {

    @Getter(AccessLevel.NONE)
    private final Map<String, Integer> parameterMap;

    @Getter(AccessLevel.NONE)
    protected final String baseResponse;
    private final int functionCode;
    private final String responseMessage;
    private final int responseCode;
    private final boolean success;

    public BasicResponse(String response)
    {
        baseResponse = response.substring(1, response.length()-2);
        parameterMap = initializeParameterMap();

        functionCode = parseIntParameter(baseResponse, parameterMap, "FunctionCode");
        responseCode = parseIntParameter(baseResponse, parameterMap, "ResponseCode");
        responseMessage = ResponseCodes.getResponseMessage(responseCode);
        success = ResponseCodes.getResponseMessage(0).equals(responseMessage);
    }

    @Override
    public String toString() {
        return "\nFunction: " + functionCode + "\n" +
            "Response code: " + responseCode + "\n" +
            "Response message: " + responseMessage + "\n" +
            "Success?: " + success;
    }

    private static Map<String, Integer> initializeParameterMap() {
        Map<String, Integer> baseMap = new HashMap<>();
        baseMap.put("FunctionCode", 0);
        baseMap.put("ResponseCode", 1);
        return Collections.unmodifiableMap(baseMap);
    }
}
