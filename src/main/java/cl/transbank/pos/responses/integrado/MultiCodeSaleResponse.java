package cl.transbank.pos.responses.integrado;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

import static cl.transbank.pos.utils.ParameterParser.*;

@Getter
public class MultiCodeSaleResponse extends SaleResponse {

    @Getter(AccessLevel.NONE)
    private final Map<String, Integer> m_parameterMap = new HashMap<String, Integer>()
    {
        {
            put("Filler", 19);
            put("Change", 20);
            put("CommerceProviderCode", 21);
        }
    };

    private final String filler;
    private final int change;
    private final long commerceProviderCode;

    public MultiCodeSaleResponse(String response) {
        super(response);
        filler = parseStringParameter(m_response, m_parameterMap, "Filler");
        change = parseIntParameter(m_response, m_parameterMap, "Change");
        commerceProviderCode = parseLongParameter(m_response, m_parameterMap, "CommerceCode");
    }

    @Override
    public String toString()
    {
        return super.toString() + "\n" +
            "Filler: " + filler + "\n" +
            "Change: " + change + "\n" +
            "Commerce Provider Code: " + commerceProviderCode;
    }
}
