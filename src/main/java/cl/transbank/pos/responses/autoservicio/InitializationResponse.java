package cl.transbank.pos.responses.autoservicio;

import cl.transbank.pos.responses.common.BasicResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static cl.transbank.pos.utils.ParameterParser.parseRealDate;

public class InitializationResponse extends BasicResponse {

    private final Map<String, Integer> m_parameterMap = new HashMap<String, Integer>()
    {
        {
            put("RealDate", 2);
            put("RealTime", 3);
        }
    };

    private final Date realDate;

    public InitializationResponse(String response) {
        super(response);
        realDate = parseRealDate(m_response, m_parameterMap);
    }

    @Override
    public String toString()
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String formattedRealDate = realDate != null ? dateFormat.format(realDate) : "";
        return super.toString() + "\n" +
                "Real Date: " + formattedRealDate;
    }
}
