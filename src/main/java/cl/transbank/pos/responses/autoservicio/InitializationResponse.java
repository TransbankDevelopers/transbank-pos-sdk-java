package cl.transbank.pos.responses.autoservicio;

import cl.transbank.pos.responses.common.BasicResponse;
import lombok.AccessLevel;
import lombok.Getter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static cl.transbank.pos.utils.ParameterParser.parseRealDate;

@Getter
public class InitializationResponse extends BasicResponse {

    @Getter(AccessLevel.NONE)
    private final Map<String, Integer> parameterMap;

    private final Date realDate;

    public InitializationResponse(String response) {
        super(response);
        parameterMap = initializeParameterMap();

        realDate = parseRealDate(baseResponse, parameterMap);
    }

    @Override
    public String toString()
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String formattedRealDate = realDate != null ? dateFormat.format(realDate) : "";
        return super.toString() + "\n" +
                "Real Date: " + formattedRealDate;
    }

    private Map<String, Integer> initializeParameterMap() {
        Map<String, Integer> baseMap = new HashMap<>();
        baseMap.put("RealDate", 2);
        baseMap.put("RealTime", 3);
        return Collections.unmodifiableMap(baseMap);
    }
}
