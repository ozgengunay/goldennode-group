package com.thingabled.server.security.hmac.filter;

public final class HMACCredentials {

	private String requestData;
	private String signature;

	@SuppressWarnings("unused")
	private HMACCredentials() {
		//
	}

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