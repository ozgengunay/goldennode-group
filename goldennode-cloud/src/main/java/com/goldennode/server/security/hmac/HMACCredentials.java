package com.goldennode.server.security.hmac;

public final class HMACCredentials {

	private String requestData;
	private String signature;

	public HMACCredentials(String requestData, String signature) {
		this.requestData = requestData;
		this.signature = signature;
	}

	public String getRequestData() {
		return requestData;
	}

	public String getSignature() {
		return signature;
	}
}