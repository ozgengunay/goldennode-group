package com.thingabled.commons.util;

public class ExceptionUtils {
	public static String getStackTrace(Throwable caught) {
		String st = caught.toString();
		for (StackTraceElement ste : caught.getStackTrace()) {
			st += "\n" + ste.toString();
		}
		Throwable tmp = caught.getCause();
		while (tmp != null) {
			st += "\n" + "Caused by";
			for (StackTraceElement ste : tmp.getStackTrace()) {
				st += "\n" + ste.toString();
			}
			tmp = tmp.getCause();
		}
		return st;
	}
}
