package com.goldennode.server.common;

import org.springframework.http.HttpStatus;

public class ApiError {
    private HttpStatus status;
    private String claz;
    private String description;

    public ApiError(HttpStatus status, String claz, String description) {
        super();
        this.status = status;
        this.claz = claz;
        this.description = description;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getClaz() {
        return claz;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}