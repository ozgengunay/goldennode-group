package com.goldennode.commons.util;

public class StringUtils {
	public static String crop(String str, int length) {
		if (str == null)
			return null;
		if (str.length() < length)
			return str;
		return str.substring(0, length);
	}
}
