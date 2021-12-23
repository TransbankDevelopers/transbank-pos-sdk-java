package cl.transbank.pos.responses.integrado;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static cl.transbank.pos.utils.ParameterParser.*;

@Getter
public class MultiCodeDetailResponse extends SaleResponse {

    @Getter(AccessLevel.NONE)
    private final Map<String, Integer> parameterMap;

    private final int change;
    private final long commerceProviderCode;

    public MultiCodeDetailResponse(String response) {
        super(response);
        parameterMap = initializeParameterMap();

        change = parseIntParameter(baseResponse, parameterMap, "Change");
        commerceProviderCode = parseLongParameter(baseResponse, parameterMap, "CommerceCode");
    }

    @Override
    public String toString()
    {
        return super.toString() + "\n" +
            "Change: " + change + "\n" +
            "Commerce Provider Code: " + commerceProviderCode;
    }

    private static Map<String, Integer> initializeParameterMap() {
        Map<String, Integer> baseMap = new HashMap<>();
        baseMap.put("Change", 19);
        baseMap.put("CommerceProviderCode", 20);
        return Collections.unmodifiableMap(baseMap);
    }
}
