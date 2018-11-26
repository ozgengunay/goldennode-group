package com.goldennode.commons.util;

import java.io.Serializable;

public class GoldenNodeException extends Exception implements Serializable {

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
