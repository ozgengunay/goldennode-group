package com.ozgen.server.common;

public class EVCloudRestException extends Exception {
    private String description;
    private String claz;

    public EVCloudRestException(Exception e) {
        super(e);
        this.claz = e.getClass().getName();
        this.description = e.getMessage() != null ? e.getMessage() : "";
    }

    public EVCloudRestException(ErrorCode description) {
        super(description.toString());
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
