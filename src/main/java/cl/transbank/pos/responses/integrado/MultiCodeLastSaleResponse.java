package cl.transbank.pos.responses.integrado;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cl.transbank.pos.utils.ParameterParser.*;

/**
 * Parsed response for multi-code last sale queries in POS Integrado.
 * <p>
 * Extends {@link SaleResponse} and adds multi-code specific fields.
 */

@Getter
public class MultiCodeLastSaleResponse extends LastSaleResponse {

    @Getter(AccessLevel.NONE)
    private final Map<String, Integer> parameterMap;

    private final List<String> voucher;
    private final int change;
    private final long commerceProviderCode;

    public MultiCodeLastSaleResponse(String response) {
        super(response);
        parameterMap = initializeParameterMap();

        voucher = parsePrintingField(baseResponse, parameterMap);
        change = parseIntParameter(baseResponse, parameterMap, "Change");
        commerceProviderCode = parseLongParameter(baseResponse, parameterMap, "CommerceCode");
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
                "Voucher: " + ((voucher.size() > 1) ? "\r\n" + String.join("\r\n", voucher) : voucher.get(0)) + "\n" +
                "Change: " + change + "\n" +
                "Commerce Provider Code: " + commerceProviderCode;
    }

    private static Map<String, Integer> initializeParameterMap() {
        Map<String, Integer> baseMap = new HashMap<>();
        baseMap.put("Voucher", 19);
        baseMap.put("Change", 20);
        baseMap.put("CommerceProviderCode", 21);
        return Collections.unmodifiableMap(baseMap);
    }
}
