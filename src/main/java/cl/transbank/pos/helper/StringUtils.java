package cl.transbank.pos.helper;

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
}
