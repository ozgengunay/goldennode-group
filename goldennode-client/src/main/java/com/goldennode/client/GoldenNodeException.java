package com.goldennode.client;

public class GoldenNodeException extends Exception{
    public GoldenNodeException() {
        super();
    }

    public GoldenNodeException(String message) {
        super(message);
    }

    public GoldenNodeException(Throwable e) {
        super(e);
    }
}
