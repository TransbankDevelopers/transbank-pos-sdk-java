package cl.transbank.pos.responses.autoservicio;

import cl.transbank.pos.responses.common.LoadKeysResponse;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cl.transbank.pos.utils.ParameterParser.parsePrintingField;

@Getter
public class CloseResponse extends LoadKeysResponse {

    @Getter(AccessLevel.NONE)
    private final Map<String, Integer> parameterMap;

    private final List<String> printingField;

    public CloseResponse(String response) {
        super(response);
        parameterMap = initializeParameterMap();

        printingField = parsePrintingField(baseResponse, parameterMap);
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
            "Printing Field: " + ((printingField.size() > 1) ? "\r\n" + String.join("\r\n", printingField) : printingField.get(0));
    }

    private static Map<String, Integer> initializeParameterMap() {
        Map<String, Integer> baseMap = new HashMap<>();
        baseMap.put("PrintingField", 4);
        return Collections.unmodifiableMap(baseMap);
    }
}
