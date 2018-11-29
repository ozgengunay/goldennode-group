package com.goldennode.server.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseEntity {
    @JsonProperty
    Object response;
    @JsonProperty
    String responseCode;

    public ResponseEntity(Object response, StatusCode responseCode) {
        this.response = response;
        this.responseCode = responseCode.toString();
    }

    public String getResponseCode() {
        return responseCode;
    }

    public Object getResponse() {
        return response;
    }
}
