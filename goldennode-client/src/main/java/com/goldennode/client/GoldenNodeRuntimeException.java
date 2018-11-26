package com.goldennode.client;

import com.goldennode.commons.util.GoldenNodeException;

public class GoldenNodeRuntimeException extends RuntimeException {

    public GoldenNodeRuntimeException(GoldenNodeException e) {
        super(e);
    }
}
