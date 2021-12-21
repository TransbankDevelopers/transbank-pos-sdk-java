package cl.transbank.pos.responses.common;

import java.util.HashMap;
import java.util.Map;

import cl.transbank.pos.utils.ResponseCodes;
import lombok.AccessLevel;
import lombok.Getter;

import static cl.transbank.pos.utils.ParameterParser.parseIntParameter;

@Getter
public class BasicResponse {
    @Getter(AccessLevel.NONE)
    private final Map<String, Integer> m_parameterMap = new HashMap<String, Integer>()
    {
        {
            put("FunctionCode", 0);
            put("ResponseCode", 1);
        }
    };

    @Getter(AccessLevel.NONE)
    protected final String m_response;
    private final int functionCode;
    private final String responseMessage;
    private final int responseCode;
    private final boolean success;

    public BasicResponse(String response)
    {
        m_response = response.substring(1, response.length()-2);
        functionCode = parseIntParameter(m_response, m_parameterMap, "FunctionCode");
        responseCode = parseIntParameter(m_response, m_parameterMap, "ResponseCode");
        responseMessage = ResponseCodes.map.getOrDefault(responseCode, "Mensaje no encontrado");
        success = ResponseCodes.map.get(0).equals(responseMessage);
    }

    @Override
    public String toString() {
        return "\nFunction: " + functionCode + "\n" +
            "Response code: " + responseCode + "\n" +
            "Response message: " + responseMessage + "\n" +
            "Success?: " + success;
    }
}
