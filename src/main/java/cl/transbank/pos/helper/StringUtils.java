package cl.transbank.pos.helper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StringUtils {

    public static boolean isEmpty(String data) {
        if (data == null || data.trim().equals("")) {
            return true;
        }
        return false;
    }

    public static boolean notEmpty(String data) {
        return !isEmpty(data);
    }

    public static int parseInt(String number) {
        if (notEmpty(number)) {
            try {
                return Integer.parseInt(number);
            } catch (Exception e) {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public static long parseLong(String number) {
        if (notEmpty(number)) {
            try {
                return Long.parseLong(number);
            } catch (Exception e) {
                return 0L;
            }
        } else {
            return 0;
        }
    }

    private static final String pad = "000000";

    public static String pad(int ticket, int padding) {
        String padded = pad + String.valueOf(ticket);
        int length = padded.length();
        return padded.substring(length - padding, length);
    }

    private final static DateTimeFormatter realDateTimeformatter = DateTimeFormatter.ofPattern("ddMMyyyy HHmmss");

    public final static LocalDateTime parseLocalDateTime(String date, String time) {
        if ("00-00-00".equals(date) || isEmpty(date)) {
            return null;
        }
        try {
            LocalDateTime result = LocalDateTime.parse(date + " " + time, realDateTimeformatter);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private final static DateTimeFormatter accountingDateTimeformatter = DateTimeFormatter.ofPattern("ddMMyyyy");

    public final static LocalDate parseLocalDate(String date) {
        if ("00-00-00".equals(date) || isEmpty(date)) {
            return null;
        }
        try {
            LocalDate result = LocalDate.parse(date + " " + date, accountingDateTimeformatter);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
