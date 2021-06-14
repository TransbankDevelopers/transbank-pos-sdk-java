package cl.transbank.pos.helper;

import cl.transbank.pos.exceptions.NotInstantiableException;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StringUtils {

    final static Logger logger = Logger.getLogger(StringUtils.class);

    private StringUtils() {
        throw new NotInstantiableException("Do not instantiate this!");
    }

    public static boolean isEmpty(String data) {
        return data == null || data.trim().equals("");
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

    private static final String PAD = "000000";

    public static String pad(int ticket, int padding) {
        return padStr(String.valueOf(ticket), padding);
    }

    public static String padStr(String ticket, int padding) {
        String padded = PAD + ticket;
        int length = padded.length();
        return padded.substring(length - padding, length);
    }


    private static final DateTimeFormatter realDateTimeformatter = DateTimeFormatter.ofPattern("ddMMyyyy HHmmss");

    public static final LocalDateTime parseLocalDateTime(String date, String time) {
        if ("00-00-00".equals(date) || isEmpty(date)) {
            return null;
        }
        try {
            return LocalDateTime.parse(date + " " + time, realDateTimeformatter);
        } catch (Exception e) {
            logger.error("Error when parsing date (" + date + ") + time (" + time + ") message: " + e, e);
            return null;
        }
    }

    private static final DateTimeFormatter accountingDateTimeformatter = DateTimeFormatter.ofPattern("ddMMyyyy");

    public static final LocalDate parseLocalDate(String date) {
        if ("00-00-00".equals(date) ||"000000".equals(date) || "--".equals(date) || isEmpty(date)) {
            return null;
        }
        try {
            return LocalDate.parse(date, accountingDateTimeformatter);
        } catch (Exception e) {
            logger.error("Error when parsing date (" + date + ") message: " + e, e);
            return null;
        }
    }

}
