package com.goldennode.server.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseEntity {
    @JsonProperty
    Object entity;
    @JsonProperty
    String code;

    public ResponseEntity(Object entity, StatusCode code) {
        this.entity = entity;
        this.code = code.toString();
    }

    public ResponseEntity(StatusCode code) {
        this.code = code.toString();
    }

    public String getCode() {
        return code;
    }

    public Object getEntity() {
        return entity;
    }
}
