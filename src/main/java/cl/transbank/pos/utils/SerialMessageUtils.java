package cl.transbank.pos.utils;

/**
 * Utility helpers for parsing and validating serial POS messages.
 */
public final class SerialMessageUtils {
    private static final char ETX = '\u0003';

    private SerialMessageUtils() {
    }

    public static boolean checkMissingEtx(String response) {
        return response.charAt(response.length() - 2) != ETX;
    }

    public static boolean checkReceivedLrc(String response) {
        if (response.isEmpty())
            return false;
        if (checkIntermediateMessage(response))
            return true;
        char received = response.charAt(response.length() - 1);
        char calculated = calculateResponseLrc(response);
        return received == calculated;
    }

    public static String getAuthorizationCode(String response) {
        String[] parts = response.split("\\|", -1);
        return parts.length > 5 ? parts[5] : "";
    }

    public static boolean checkIntermediateMessage(String response) {
        if (response.length() >= 1) {
            String payload = response.substring(1, response.length() - 2);
            return getFunctionCode(payload).equals("0900");
        }

        return false;
    }

    private static char calculateResponseLrc(String message) {
        String trimmed = message.substring(1, message.length() - 1);
        return calculateLrc(trimmed);
    }

    private static char calculateLrc(String message) {
        char x = 0;
        for (int i = 0; i < message.length(); i++)
            x ^= message.charAt(i);
        return x;
    }

    private static String getFunctionCode(String response) {
        return response.split("\\|", -1)[0];
    }
}
