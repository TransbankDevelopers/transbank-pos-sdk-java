package cl.transbank.pos.responses.integrado;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static cl.transbank.pos.utils.ParameterParser.*;

@Getter
public class MultiCodeSaleResponse extends SaleResponse {

    @Getter(AccessLevel.NONE)
    private final Map<String, Integer> parameterMap;

    private final String filler;
    private final int change;
    private final long commerceProviderCode;

    public MultiCodeSaleResponse(String response) {
        super(response);
        parameterMap = initializeParameterMap();

        filler = parseStringParameter(baseResponse, parameterMap, "Filler");
        change = parseIntParameter(baseResponse, parameterMap, "Change");
        commerceProviderCode = parseLongParameter(baseResponse, parameterMap, "CommerceCode");
    }

    @Override
    public String toString()
    {
        return super.toString() + "\n" +
            "Filler: " + filler + "\n" +
            "Change: " + change + "\n" +
            "Commerce Provider Code: " + commerceProviderCode;
    }

    private static Map<String, Integer> initializeParameterMap() {
        Map<String, Integer> baseMap = new HashMap<>();
        baseMap.put("Filler", 19);
        baseMap.put("Change", 20);
        baseMap.put("CommerceProviderCode", 21);
        return Collections.unmodifiableMap(baseMap);
    }
}
