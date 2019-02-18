package com.goldennode.client;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class Response {
    String body;
    int statusCode;
    Object entityValue;

    public Response(String body, int statusCode) throws GoldenNodeException {
        this.body = body;
        this.statusCode = statusCode;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = null;
        try {
            json = mapper.readTree(body);
        } catch (Exception e1) {
            throw new GoldenNodeException("invalid response");
        }
        if (json.has("entity")) {
            entityValue = getNodeValue(json.get("entity"));
        }
        if (json.has("error")) {
            raiseError(json.get("error"));
        }
    }

    private void raiseError(JsonNode node) throws GoldenNodeException {
        try {
            Class<?> c = Class.forName(node.get("claz").asText());
            Constructor<?> con = c.getConstructor(String.class);
            Exception e = (Exception) con.newInstance(node.get("description").asText());
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
            throw new GoldenNodeException(e);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
            //
        }
    }

    private Object getNodeValue(JsonNode node) throws GoldenNodeException {
        JsonNodeType type = node.getNodeType();
        switch (type) {
            case STRING:
                if (Utils.canExtractObject(node.asText())) {
                    return Utils.extractObject(node.asText());
                } else {
                    return node.asText();
                }
            case ARRAY:
                List<Object> list = new ArrayList<>();
                Iterator<JsonNode> iter = node.iterator();
                while (iter.hasNext()) {
                    JsonNode nde = iter.next();
                    list.add(getNodeValue(nde));
                }
                return list.toArray();
            case BINARY:
                try {
                    return node.binaryValue();
                } catch (IOException e) {
                    throw new GoldenNodeException("invalid value");
                }
            case BOOLEAN:
                return node.asBoolean();
            case MISSING:
            case NULL:
                return null;
            case NUMBER:
                if (node.isInt()) {
                    return node.asInt();
                } else if (node.isLong()) {
                    return node.asLong();
                } else if (node.isFloat() || node.isDouble()) {
                    return node.asDouble();
                }
            case OBJECT:
            case POJO:
                return new Map.Entry<Object, Object>() {
                    @Override
                    public Object getKey() {
                        return Utils.extractObject((String) node.fieldNames().next());
                    }

                    @Override
                    public Object getValue() {
                        return Utils.extractObject((String) node.fields().next().getValue().asText());
                    }

                    @Override
                    public Object setValue(Object value) {
                        return null;
                    }
                };
            default:
                throw new GoldenNodeException("invalid type");
        }
    }

    public Object getEntityValue() {
        return entityValue;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }
}
