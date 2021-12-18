package cl.transbank.pos.utils;

import java.util.Map;

public class ParameterParser {

    public static String parseStringParameter(String response, Map<String, Integer> parameterMap, String parameterKey) {
        try {
            return response.split("\\|")[parameterMap.get(parameterKey)].trim();
        }
        catch (ArrayIndexOutOfBoundsException ex) {}
        return "";
    }

    public static int parseIntParameter(String response, Map<String, Integer> parameterMap, String parameterKey) {
        try {
            return Integer.parseInt(response.split("\\|")[parameterMap.get(parameterKey)].trim());
        }
        catch (ArrayIndexOutOfBoundsException | NumberFormatException ex) {}
        return -1;
    }

    public static long parseLongParameter(String response, Map<String, Integer> parameterMap, String parameterKey) {
        try {
            return Long.parseLong(response.split("\\|")[parameterMap.get(parameterKey)].trim());
        }
        catch (ArrayIndexOutOfBoundsException | NumberFormatException ex) {}
        return -1;
    }
}
