package com.goldennode.client;

import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Response {
    String response;
    int responseCode;
    String value;

    public Response(String response, int responseCode) {
        this.response = response;
        this.responseCode = responseCode;
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(response);
            if (json.has("response")) {
                value = json.get("response").asText();
            } else {
                value = null;
            }
        } catch (IOException e) {
            //invalid json
        }
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponse() {
        return response;
    }

    public String getValue() {
        return value;
    }
}
