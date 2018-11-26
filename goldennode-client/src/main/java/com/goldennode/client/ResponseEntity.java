package com.goldennode.client;

public class ResponseEntity {
    String body;
    int responseCode;

    public ResponseEntity(int responseCode, String body) {
        this.responseCode = responseCode;
        this.body = body;
    }
}
