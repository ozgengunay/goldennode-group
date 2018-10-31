package com.thingabled.commons.util;

import java.io.Serializable;

public class ThingabledException extends Exception implements Serializable {

	public ThingabledException() {
		super();
	}

	public ThingabledException(String message) {
		super(message);
	}

}
