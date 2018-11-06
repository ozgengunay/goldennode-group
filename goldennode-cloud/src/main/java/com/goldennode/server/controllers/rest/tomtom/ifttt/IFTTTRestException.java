package com.goldennode.server.controllers.rest.tomtom.ifttt;

import com.goldennode.commons.util.GoldenNodeException;

public class IFTTTRestException extends GoldenNodeException {

	private String error;

	public IFTTTRestException(String error) {

		super(error);
		this.error = error;
	}

	public String getError() {
		return error;
	}

}
