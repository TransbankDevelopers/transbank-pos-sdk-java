package cl.transbank.pos.responses.integrado;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import cl.transbank.pos.responses.common.BasicResponse;
import lombok.AccessLevel;
import lombok.Getter;

import static cl.transbank.pos.utils.ParameterParser.parseIntParameter;

@Getter
public class TotalsResponse extends BasicResponse {

    @Getter(AccessLevel.NONE)
    private final Map<String, Integer> parameterMap;

    private final int txCount;
    private final int txTotal;

    public TotalsResponse(String response) {
        super(response);
        parameterMap = initializeParameterMap();

        txCount = parseIntParameter(baseResponse, parameterMap, "TxCount");
        txTotal = parseIntParameter(baseResponse, parameterMap, "TxTotal");
    }

    @Override
    public String toString()
    {
        return super.toString() + "\n" +
            "TX Count: " + txCount + "\n" +
            "TX Total: " + txTotal;
    }

    private Map<String, Integer> initializeParameterMap() {
        Map<String, Integer> baseMap = new HashMap<>();
        baseMap.put("TxCount", 2);
        baseMap.put("TxTotal", 3);
        return Collections.unmodifiableMap(baseMap);
    }
}
