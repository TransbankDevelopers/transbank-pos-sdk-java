package cl.transbank.pos.responses.commonResponses;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

import static cl.transbank.pos.utils.ParameterParser.parseIntParameter;
import static cl.transbank.pos.utils.ParameterParser.parseStringParameter;

@Getter
public class RefundResponse extends LoadKeysResponse {

    private final Map<String, Integer> m_parameterMap = new HashMap<String, Integer>()
    {
        {
            put("AuthorizationCode", 4);
            put("OperationID", 5);
        }
    };

    private final String authorizationCode;
    private final int operationID;

    public RefundResponse(String response) {
        super(response);
        authorizationCode = parseStringParameter(m_response, m_parameterMap, "AuthorizationCode");
        operationID = parseIntParameter(m_response, m_parameterMap, "OperationID");
    }

    @Override
    public String toString()
    {
        return super.toString() + "\n" +
                "AuthorizationCode: " + authorizationCode + "\n" +
                "OperationID: " + operationID;
    }
}

