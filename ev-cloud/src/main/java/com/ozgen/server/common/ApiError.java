package com.ozgen.server.common;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeInfo(include = As.WRAPPER_OBJECT, use = Id.NAME)
@JsonTypeName(value = "error")
public class ApiError {
    private String claz;
    private String description;

    public ApiError(String claz, String description) {
        this.claz = claz;
        this.description = description;
    }

    public String getClaz() {
        return claz;
    }

    public String getDescription() {
        return description;
    }
}