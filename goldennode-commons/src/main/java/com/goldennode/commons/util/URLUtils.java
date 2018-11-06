package com.goldennode.commons.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class URLUtils {

	public static String fullEncode(String str) {
		try {
			return URLEncoder.encode(str, "UTF-8").replace("+", "%20").replace("+", "%20").replace("_", "%5F")
					.replace(".", "%2E").replace("-", "%2D");
		} catch (UnsupportedEncodingException e) {
			return null;//will not happen
		}
		

	}
	
	public static String encode(String str) {
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;//will not happen
		}
		

	}
}
