package com.goldennode.server.common;

import com.goldennode.commons.util.GoldenNodeException;

public class GoldenNodeRestException extends GoldenNodeException{

	private ErrorCode code;

	public GoldenNodeRestException(ErrorCode code) {
		
		super(code.toString());
		this.code=code;
	}

	public ErrorCode getCode() {
		return code;
	}


}
