package com.goldennode.client;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Utils {
    public static String encode(String str) {
        try {
            return URLEncoder.encode(str.replace("/", "&sol;").replace("\"", "&quot;").replace("\\", "&bsol;"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
