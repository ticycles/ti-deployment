package com.trackandtrail.util;

public class OperatingSystemValidator {
	
	private static String OperatingSystem = System.getProperty("os.name").toLowerCase();

	public static boolean isWindows() {

		return (OperatingSystem.indexOf("win") >= 0);

	}

	public static boolean isMac() {

		return (OperatingSystem.indexOf("mac") >= 0);

	}

	public static boolean isUnix() {

		return (OperatingSystem.indexOf("nix") >= 0 || OperatingSystem.indexOf("nux") >= 0 || OperatingSystem.indexOf("aix") > 0);

	}

	public static boolean isSolaris() {

		return (OperatingSystem.indexOf("sunos") >= 0);

	}
}
