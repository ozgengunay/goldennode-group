package com.goldennode.server.controllers.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice(basePackages="com.goldennode.server.controllers.rest.controllers")
public class CustomRestExceptionHandler {

	@ExceptionHandler({ GoldenNodeRestException.class })
	public ResponseEntity<Object> handleAll(GoldenNodeRestException ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex,ex.getCode());
		return new ResponseEntity<Object>(apiError, apiError.getStatus());
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,ex, ErrorCode.GENERAL_ERROR);
		return new ResponseEntity<Object>(apiError, apiError.getStatus());
	}

}



 