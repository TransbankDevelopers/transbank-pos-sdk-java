package cl.transbank.pos.responses.integradoResponses;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

import static cl.transbank.pos.utils.ParameterParser.*;

@Getter
public class MultiCodeLastSaleResponse extends LastSaleResponse {
    @Getter(AccessLevel.NONE)
    private final Map<String, Integer> m_parameterMap = new HashMap<String, Integer>()
    {
        {
            put("Voucher", 19);
            put("Change", 20);
            put("CommerceProviderCode", 21);
        }
    };

    private final String voucher;
    private final int change;
    private final long commerceProviderCode;

    public MultiCodeLastSaleResponse(String response) {
        super(response);
        voucher = parseStringParameter(m_response, m_parameterMap, "Voucher");
        change = parseIntParameter(m_response, m_parameterMap, "Change");
        commerceProviderCode = parseLongParameter(m_response, m_parameterMap, "CommerceCode");
    }

    @Override
    public String toString()
    {
        return super.toString() + "\n" +
            "Voucher: " + voucher + "\n" +
            "Change: " + change + "\n" +
            "Commerce Provider Code: " + commerceProviderCode;
    }
}
