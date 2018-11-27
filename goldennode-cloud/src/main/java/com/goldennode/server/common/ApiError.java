package com.goldennode.server.common;

import org.springframework.http.HttpStatus;

public class ApiError {

	private HttpStatus status;
	private String exception;
	private ErrorCode code;
	public ApiError(HttpStatus status, Exception exception,ErrorCode code) {
		super();
		this.status = status;
		this.exception = exception.toString();
		this.code = code;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public ErrorCode getCode() {
		return code;
	}

	public void setCode(ErrorCode code) {
		this.code = code;
	}




}