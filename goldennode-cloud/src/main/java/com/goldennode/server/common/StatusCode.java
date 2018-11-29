package com.goldennode.server.common;

public enum StatusCode {
    SUCCESS("SUCCESS");
    private final String name;

    private StatusCode(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}