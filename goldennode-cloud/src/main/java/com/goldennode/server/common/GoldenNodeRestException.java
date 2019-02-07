package com.goldennode.server.common;

public class GoldenNodeRestException extends Exception {
    private String description;
    private String claz;

    public GoldenNodeRestException(Exception e) {
        this.claz = e.getClass().getName();
        this.description = e.toString();
    }

    public GoldenNodeRestException(ErrorCode description) {
        this.claz = Exception.class.getName();
        this.description = description.toString();
    }

    public String getDescription() {
        return description;
    }

    public String getClaz() {
        return claz;
    }
}
