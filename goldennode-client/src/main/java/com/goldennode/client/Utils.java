package com.goldennode.client;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Utils {
    public static String encode(String str) {
        try {
            return URLEncoder.encode(str.replace("/", "&sol;").replace("\"", "&quot;").replace("\\", "&bsol;"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
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
}
