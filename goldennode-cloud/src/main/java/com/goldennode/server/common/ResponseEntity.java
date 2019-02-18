package com.goldennode.server.common;

import com.fasterxml.jackson.annotation.JsonProperty;

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
