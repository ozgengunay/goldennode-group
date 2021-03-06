package com.goldennode.server.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice(basePackages = "com.goldennode.server.controllers")
public class CustomRestExceptionHandler {
    @ExceptionHandler({ GoldenNodeRestException.class })
    public ResponseEntity<Object> handleAll(GoldenNodeRestException ex, WebRequest request) {
        ApiError apiError = new ApiError(ex.getClaz(), ex.getDescription());
        return new ResponseEntity<Object>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        ApiError apiError = new ApiError(ex.getClass().getName(), ex.getMessage() != null ? ex.getMessage() : "");
        return new ResponseEntity<Object>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}