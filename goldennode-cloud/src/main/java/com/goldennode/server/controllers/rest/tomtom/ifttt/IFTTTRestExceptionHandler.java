package com.goldennode.server.controllers.rest.tomtom.ifttt;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice(basePackages = "com.thingabled.server.controllers.rest.tomtom.ifttt")
public class IFTTTRestExceptionHandler {

	@ExceptionHandler({ IFTTTRestException.class })
	public ResponseEntity<Object> handleAll(IFTTTRestException ex, WebRequest request) {

		String jsonObject = "{\"errors\": [{\"message\": \"" + ex.getError() + "\"}]}";
		return new ResponseEntity<Object>(jsonObject, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
		return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
