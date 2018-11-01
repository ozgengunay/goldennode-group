package com.goldennode.server;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.crypto.codec.Base64;

public class ClientAuthenticationSample {

	public static void main(String[] args) {
		//try {
			
			
			String auth="Authorization: Basic " + new String (Base64.encode("wakccrevor_1497268267@tfbnw.net:12345678".getBytes()));
			System.out.println(auth);
			//sampleRequest();
		/*} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}*/

	}

	private static void sampleRequest() throws GeneralSecurityException {

	/*	String uri = "/server/getLocation/locationId/1";
				
		String body="{\"id\":\"1234\",\"unit\":\"C\",\"value\":\"33\"}";
		LOGGER.debug("body > " + body);
		String publicKey = "public1";
		String secretKey = "secret1";
		String auth = "key=" + publicKey + ",timestamp=" + System.currentTimeMillis() / 1000L + ",nonce="
				+ UUID.getUUID();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Signature", generateHmacSHA256Signature(auth + uri + body, secretKey));
		LOGGER.debug("Signature to be generated : "+generateHmacSHA256Signature(auth + uri + body, secretKey));
		headers.add("Authorization", auth);
		//headers.add("Content-Type", "application/json");

		LOGGER.debug("Signature=" + headers.get("Signature"));
		LOGGER.debug("Authorization=" + headers.get("Authorization"));
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);
		LOGGER.debug("Request=" + entity.toString());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		LOGGER.debug(result);*/

	}

	private static String generateHmacSHA256Signature(String data, String secretKey) throws GeneralSecurityException {
		byte[] hmacData = null;

		try {
			SecretKeySpec sKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(sKey);
			hmacData = mac.doFinal(data.getBytes("UTF-8"));
			return new String(Base64.encode(hmacData), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new GeneralSecurityException(e);
		}
	}

}