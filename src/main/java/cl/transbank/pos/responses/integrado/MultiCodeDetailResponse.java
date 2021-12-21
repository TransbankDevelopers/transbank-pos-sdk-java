package cl.transbank.pos.responses.integrado;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

import static cl.transbank.pos.utils.ParameterParser.*;

@Getter
public class MultiCodeDetailResponse extends SaleResponse {
    @Getter(AccessLevel.NONE)
    private final Map<String, Integer> m_parameterMap = new HashMap<String, Integer>()
    {
        {
            put("Change", 19);
            put("CommerceProviderCode", 20);
        }
    };

    private final int change;
    private final long commerceProviderCode;

    public MultiCodeDetailResponse(String response) {
        super(response);
        change = parseIntParameter(m_response, m_parameterMap, "Change");
        commerceProviderCode = parseLongParameter(m_response, m_parameterMap, "CommerceCode");
    }

    @Override
    public String toString()
    {
        return super.toString() + "\n" +
            "Change: " + change + "\n" +
            "Commerce Provider Code: " + commerceProviderCode;
    }
}
