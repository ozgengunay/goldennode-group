package com.thingabled.server.controllers.rest.tomtom.ifttt;

import com.thingabled.commons.util.ThingabledException;

public class IFTTTRestException extends ThingabledException {

	private String error;

	public IFTTTRestException(String error) {

		super(error);
		this.error = error;
	}

	public String getError() {
		return error;
	}

}
