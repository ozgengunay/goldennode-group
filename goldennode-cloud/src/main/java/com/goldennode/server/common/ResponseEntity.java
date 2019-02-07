package com.goldennode.server.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseEntity {
    @JsonProperty
    Object entity;

    public ResponseEntity(Object entity) {
        this.entity = entity;
    }

    public ResponseEntity() {
    }

    public Object getEntity() {
        return entity;
    }
}
