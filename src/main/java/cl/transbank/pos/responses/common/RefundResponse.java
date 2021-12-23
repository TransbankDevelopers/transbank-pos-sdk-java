package cl.transbank.pos.responses.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Getter;

import static cl.transbank.pos.utils.ParameterParser.parseIntParameter;
import static cl.transbank.pos.utils.ParameterParser.parseStringParameter;

@Getter
public class RefundResponse extends LoadKeysResponse {

    @Getter(AccessLevel.NONE)
    private final Map<String, Integer> parameterMap;

    private final String authorizationCode;
    private final int operationID;

    public RefundResponse(String response) {
        super(response);
        parameterMap = initializeParameterMap();

        authorizationCode = parseStringParameter(baseResponse, parameterMap, "AuthorizationCode");
        operationID = parseIntParameter(baseResponse, parameterMap, "OperationID");
    }

    @Override
    public String toString()
    {
        return super.toString() + "\n" +
            "AuthorizationCode: " + authorizationCode + "\n" +
            "OperationID: " + operationID;
    }

    private static Map<String, Integer> initializeParameterMap() {
        Map<String, Integer> baseMap = new HashMap<>();
        baseMap.put("AuthorizationCode", 4);
        baseMap.put("OperationID", 5);
        return Collections.unmodifiableMap(baseMap);
    }
}

