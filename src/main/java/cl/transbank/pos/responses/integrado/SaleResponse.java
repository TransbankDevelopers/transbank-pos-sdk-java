package cl.transbank.pos.responses.integrado;

import cl.transbank.pos.responses.common.LoadKeysResponse;
import lombok.AccessLevel;
import lombok.Getter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cl.transbank.pos.utils.ParameterParser.*;

@Getter
public class SaleResponse extends BaseSaleResponse {

    @Getter(AccessLevel.NONE)
    private final Map<String, Integer> parameterMap;

    private final List<String> printingField;

    public SaleResponse(String response) {
        super(response);
        parameterMap = initializeParameterMap();
        printingField = parsePrintingField(baseResponse, parameterMap);
    }

    @Override
    public String toString() {
        String formattedPrintingField = printingField.size() > 1 ? String.join("\n", printingField) : "";
        return super.toString() + "\n" +
                "PrintingField: "
                + "\n" + formattedPrintingField;
    }

    private static Map<String, Integer> initializeParameterMap() {
        Map<String, Integer> baseMap = new HashMap<>();
        baseMap.put("PrintingField", 19);
        return Collections.unmodifiableMap(baseMap);
    }
}
