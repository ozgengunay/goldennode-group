package com.goldennode.client;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Utils {
    public static String encode(String str) {
        try {
            return URLEncoder.encode(str.replace("/", "&sol;").replace("\"", "&quot;").replace("\\", "&bsol;"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean canExtractObject(String value) {
        try {
            extractObject(value);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public static Object extractObject(String value) {
        try {
            ObjectMapper om = new ObjectMapper();
            if (value == null)
                return null;
            JsonNode node = om.readTree(value);
            String className = node.get("c").asText();
            JsonNode objectNode = node.get("o");
            if (className.equals("NULL")) {
                return null;
            }
            Class<?> c = Class.forName(className);
            return om.treeToValue(objectNode, c);
        } catch (Exception e) {
            return new RuntimeException(e);
        }
    }

    public static String encapObject(Object value) {
        try {
            ObjectMapper om = new ObjectMapper();
            JsonNode newNode = om.createObjectNode();
            ((ObjectNode) newNode).put("c", value == null ? "NULL" : value.getClass().getName());
            ((ObjectNode) newNode).set("o", om.valueToTree(value == null ? "NULL" : value));
            return om.writeValueAsString(newNode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toJsonString(Collection<?> c) {
        Iterator<?> iter = c.iterator();
        List<String> temp = new ArrayList<>();
        while (iter.hasNext()) {
            Object o = iter.next();
            temp.add(Utils.encapObject(o));
        }
        try {
            return new ObjectMapper().writeValueAsString(temp);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <K, V> String toJsonString(Map<? extends K, ? extends V> m) {
        Map<String, String> temp = new HashMap<>();
        for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
            temp.put(Utils.encapObject(entry.getKey()), Utils.encapObject(entry.getValue()));
        }
        try {
            return new ObjectMapper().writeValueAsString(temp);
            
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static long getFreeMemory() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
    }
}
