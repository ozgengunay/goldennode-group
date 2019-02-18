package com.goldennode.client;

public class GoldenNodeRuntimeException extends RuntimeException {
    public GoldenNodeRuntimeException(GoldenNodeException e) {
        super(e);
    }
}
