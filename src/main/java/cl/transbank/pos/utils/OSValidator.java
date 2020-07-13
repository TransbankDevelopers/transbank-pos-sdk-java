package cl.transbank.pos.utils;

//Reference: https://mkyong.com/java/how-to-detect-os-in-java-systemgetpropertyosname/#:~:text=Here%20is%20a%20handy%20Java,Unix%E2%80%9D%20and%20%E2%80%9CSolaris%E2%80%9D.

public class OSValidator {

	private static String OS = System.getProperty("os.name").toLowerCase();

	public static boolean isWindows() {

		return (OS.indexOf("win") >= 0);

	}

	public static boolean isMac() {

		return (OS.indexOf("mac") >= 0);

	}

	public static boolean isUnix() {

		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );

	}

	public static boolean isSolaris() {

		return (OS.indexOf("sunos") >= 0);

	}

}
