package com.goldennode.commons.util;

public class SystemUtils {
    public static String getSystemProperty(String defaultValue, String systemProperty) {
        if (System.getProperty(systemProperty) == null) {
            return defaultValue;
        } else {
            return System.getProperty(systemProperty);
        }
    }
}
