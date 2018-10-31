package com.thingabled.server.controllers.rest;

import com.thingabled.commons.util.ThingabledException;

public class ThingabledRestException extends ThingabledException{

	private ErrorCode code;

	public ThingabledRestException(ErrorCode code) {
		
		super(code.toString());
		this.code=code;
	}

	public ErrorCode getCode() {
		return code;
	}


}
