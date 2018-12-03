package com.goldennode.client;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Response {
    String body;
    int statusCode;
    String entityValue;
    Iterator<JsonNode> entityIterator;

    public Response(String body, int statusCode) {
        this.body = body;
        this.statusCode = statusCode;
        try {
            ObjectMapper mapper = new ObjectMapper();
            if (!body.isEmpty()) {
                JsonNode json = mapper.readTree(body);
                if (json.has("entity")) {
                    if (json.get("entity").isArray()) {
                        entityIterator = json.get("entity").iterator();
                    } else {
                        entityValue = json.get("entity").asText();
                    }
                } else {
                    entityValue = null;
                }
            }
        } catch (IOException e) {
            // invalid json
        }
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }

    public String getEntityValue() {
        return entityValue;
    }

    public Iterator<JsonNode> getEntityIterator() {
        return entityIterator;
    }
}
