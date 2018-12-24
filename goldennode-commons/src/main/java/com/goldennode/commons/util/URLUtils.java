package com.goldennode.commons.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class URLUtils {
    public static String unescapeSpecialChars(String str) {
        return str.replace("&sol;", "/").replace("&quot;", "\"").replace("&bsol;", "\\");
    }

    public static String escapeSpecialChars(String str) {
        return str.replace("/", "&sol;").replace("\"", "&quot;").replace("\\", "&bsol;");
    }

    public static String escape(String str) {
        try {
            return URLEncoder.encode(escapeSpecialChars(str), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
