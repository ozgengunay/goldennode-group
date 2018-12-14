package com.goldennode.server.common;

public enum ErrorCode{
	GENERAL_ERROR("general error");

	private final String name;

	private ErrorCode(String s) {
		name = s;
	}

	public boolean equalsName(String otherName) {
		return (otherName == null) ? false : name.equals(otherName);
	}

	public String toString() {
		return this.name;
	}

}