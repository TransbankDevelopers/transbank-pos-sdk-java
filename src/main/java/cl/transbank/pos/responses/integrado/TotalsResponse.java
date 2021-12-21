package cl.transbank.pos.responses.integrado;

import java.util.HashMap;
import java.util.Map;

import cl.transbank.pos.responses.common.BasicResponse;
import lombok.AccessLevel;
import lombok.Getter;

import static cl.transbank.pos.utils.ParameterParser.parseIntParameter;

@Getter
public class TotalsResponse extends BasicResponse {

    @Getter(AccessLevel.NONE)
    private final Map<String, Integer> m_parameterMap = new HashMap<String, Integer>()
    {
        {
            put("TxCount", 2);
            put("TxTotal", 3);
        }
    };

    private final int txCount;
    private final int txTotal;

    public TotalsResponse(String response) {
        super(response);
        txCount = parseIntParameter(m_response, m_parameterMap, "TxCount");
        txTotal = parseIntParameter(m_response, m_parameterMap, "TxTotal");
    }

    @Override
    public String toString()
    {
        return super.toString() + "\n" +
            "TX Count: " + txCount + "\n" +
            "TX Total: " + txTotal;
    }
}
