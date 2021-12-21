package cl.transbank.pos.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ParameterParser {

    public static String parseStringParameter(String response, Map<String, Integer> parameterMap, String parameterKey) {
        try {
            return response.split("\\|")[parameterMap.get(parameterKey)].trim();
        }
        catch (ArrayIndexOutOfBoundsException ex) { return ""; }
    }

    public static int parseIntParameter(String response, Map<String, Integer> parameterMap, String parameterKey) {
        try {
            return Integer.parseInt(response.split("\\|")[parameterMap.get(parameterKey)].trim());
        }
        catch (ArrayIndexOutOfBoundsException | NumberFormatException ex) { return -1; }
    }

    public static long parseLongParameter(String response, Map<String, Integer> parameterMap, String parameterKey) {
        try {
            return Long.parseLong(response.split("\\|")[parameterMap.get(parameterKey)].trim());
        }
        catch (ArrayIndexOutOfBoundsException | NumberFormatException ex) { return -1; }
    }

    public static Date parseAccountingDate(String response, Map<String, Integer> parameterMap) {
        try {
            String date = response.split("\\|")[parameterMap.get("AccountingDate")].trim();
            return new SimpleDateFormat("ddMMyyyy").parse(date);
        }
        catch (ArrayIndexOutOfBoundsException | ParseException ex) { return null; }
    }

    public static Date parseRealDate(String response, Map<String, Integer> parameterMap) {
        try {
            String date = response.split("\\|")[parameterMap.get("RealDate")].trim();
            String hour = response.split("\\|")[parameterMap.get("RealTime")].trim();
            return new SimpleDateFormat("ddMMyyyyHHmmss").parse(date+hour);
        }
        catch (ArrayIndexOutOfBoundsException | ParseException ex) { return null; }
    }

    public static List<String> parsePrintingField(String response, Map<String, Integer> parameterMap) {
        List<String> printingField = new ArrayList<>();

        try {
            String[] arrayResponse = response.split("\\|");
            if (arrayResponse.length < 5) {
                printingField.add("");
                return printingField;
            }

            String fullPrintingField = arrayResponse[parameterMap.get("PrintingField")].trim();

            if (fullPrintingField.length() % 40 != 0)
            {
                printingField.add(fullPrintingField);
                return printingField;
            }

            for (int i = 0; i < fullPrintingField.length(); i += 40)
                printingField.add(fullPrintingField.substring(i, 40));

            return printingField;

        } catch (IndexOutOfBoundsException e) { return printingField; }
    }
}
