package cl.transbank.pos.responses.autoservicio;

import cl.transbank.pos.responses.common.LoadKeysResponse;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cl.transbank.pos.utils.ParameterParser.parsePrintingField;

@Getter
public class CloseResponse extends LoadKeysResponse {

    @Getter(AccessLevel.NONE)
    private final Map<String, Integer> m_parameterMap = new HashMap<String, Integer>()
    {
        {
            put("PrintingField", 4);
        }
    };

    private final List<String> printingField;

    public CloseResponse(String response) {
        super(response);
        printingField = parsePrintingField(m_response, m_parameterMap);
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
            "Printing Field: " + ((printingField.size() > 1) ? "\r\n" + String.join("\r\n", printingField) : printingField.get(0));
    }
}
