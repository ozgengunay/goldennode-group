package com.goldennode.commons.util;

public class StringUtils {
    public static String shortProcessId(String id) {
        return id.length() > 6 ? id.toString().substring(id.length() - 6, id.length()) : id;
    }
    
    public static String shortId(String id) {
        return id.length() > 4 ? id.toString().substring(id.length() - 4, id.length()) : id;
    }
    public static String crop(String str, int length) {
        if (str == null)
            return null;
        if (str.length() < length)
            return str;
        return str.substring(0, length);
    }
    
    
    
}
