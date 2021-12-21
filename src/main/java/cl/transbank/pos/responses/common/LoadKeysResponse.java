package cl.transbank.pos.responses.common;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Getter;

import static cl.transbank.pos.utils.ParameterParser.parseLongParameter;
import static cl.transbank.pos.utils.ParameterParser.parseStringParameter;

@Getter
public class LoadKeysResponse extends BasicResponse {

    @Getter(AccessLevel.NONE)
    private final Map<String, Integer> m_parameterMap = new HashMap<String, Integer>()
    {
        {
            put("CommerceCode", 2);
            put("TerminalId", 3);
        }
    };

    private final long commerceCode;
    private final String terminalId;

    public LoadKeysResponse(String response) {
        super(response);
        commerceCode = parseLongParameter(m_response, m_parameterMap, "CommerceCode");
        terminalId = parseStringParameter(m_response, m_parameterMap, "TerminalId");
    }

    @Override
    public String toString()
    {
        return super.toString() + "\n" +
            "Commerce Code: " + commerceCode + "\n" +
            "Terminal Id: " + terminalId;
    }
}
