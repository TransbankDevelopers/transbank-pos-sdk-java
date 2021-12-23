package cl.transbank.pos.responses.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Getter;

import static cl.transbank.pos.utils.ParameterParser.parseLongParameter;
import static cl.transbank.pos.utils.ParameterParser.parseStringParameter;

@Getter
public class LoadKeysResponse extends BasicResponse {

    @Getter(AccessLevel.NONE)
    private final Map<String, Integer> parameterMap;

    private final long commerceCode;
    private final String terminalId;

    public LoadKeysResponse(String response) {
        super(response);
        parameterMap = initializeParameterMap();

        commerceCode = parseLongParameter(baseResponse, parameterMap, "CommerceCode");
        terminalId = parseStringParameter(baseResponse, parameterMap, "TerminalId");
    }

    @Override
    public String toString()
    {
        return super.toString() + "\n" +
            "Commerce Code: " + commerceCode + "\n" +
            "Terminal Id: " + terminalId;
    }

    private static Map<String, Integer> initializeParameterMap() {
        Map<String, Integer> baseMap = new HashMap<>();
        baseMap.put("CommerceCode", 2);
        baseMap.put("TerminalId", 3);
        return Collections.unmodifiableMap(baseMap);
    }
}
