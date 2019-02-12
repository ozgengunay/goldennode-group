package com.goldennode.client;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
            if (json.get("entity").isArray()) {
                List<Object> list = new ArrayList<>();
                Iterator<JsonNode> iter = json.get("entity").iterator();
                while (iter.hasNext()) {
                    JsonNode node = iter.next();
                    if (node.isTextual()) {
                        list.add(node.asText());
                    } else {
                        list.add(node);
                    }
                }
                entityValue = list;
            } else {
                entityValue = json.get("entity").asText();
                if (Utils.canExtractObject((String) entityValue)) {
                    entityValue = Utils.extractObject((String) entityValue);
                    json.isValueNode()
                }
            }
        } else {
            entityValue = null;// response is null
        }
        if (json.has("error")) {
            try {
                Class c = Class.forName(json.get("error").get("claz").asText());
                Constructor con = c.getConstructor(String.class);
                Exception e = (Exception) con.newInstance(json.get("error").get("description").asText());
                throw new GoldenNodeException(e);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
                //
            }
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
