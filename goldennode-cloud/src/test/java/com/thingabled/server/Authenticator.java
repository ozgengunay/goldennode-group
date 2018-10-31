package com.thingabled.server;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.util.MultiValueMap;

import com.thingabled.commons.util.UUID;

public class Authenticator {
	private static final Logger LOGGER = LoggerFactory.getLogger(Authenticator.class);
	public static final String PUBLIC_KEY="961c133a-5793-4366-9a3b-6a1880742032";
	public static final String SECRET_KEY="694614b5-faa8-411e-81a9-0ded0d990c48";
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static RequestEntity<?> signRequest(RequestEntity<?> entity) {
		String auth = "key=" + PUBLIC_KEY + ",timestamp=" + System.currentTimeMillis() / 1000L + ",nonce="
				+ UUID.getUUID();
		HttpHeaders headers=new HttpHeaders();
		
		MultiValueMap<String, String> map=entity.getHeaders();
		Iterator<Entry<String, List<String>>> iter= map.entrySet().iterator();
		while(iter.hasNext()){
			Entry<String, List<String>> entry=iter.next();
			List<String> list=entry.getValue();
			Iterator<String> iter2= list.iterator();
			while(iter2.hasNext()){
				String singleValue=iter2.next();
				headers.add(entry.getKey(), singleValue);
			}
			
		}
		headers.add("Signature", generateHmacSHA256Signature(auth + entity.getUrl().getPath() + (entity.getBody()==null?"":entity.getBody()), SECRET_KEY));
		LOGGER.debug("Signature to be created from : " + auth + entity.getUrl().getPath() +  (entity.getBody()==null?"":entity.getBody()));
		headers.add("Authorization", auth);
		return new RequestEntity(entity.getBody(),headers, entity.getMethod(), entity.getUrl());
	}

	private static String generateHmacSHA256Signature(String data, String secretKey) {
		byte[] hmacData = null;

		try {
			SecretKeySpec sKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(sKey);
			hmacData = mac.doFinal(data.getBytes("UTF-8"));
			return new String(Base64.encode(hmacData), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		} catch (NoSuchAlgorithmException e) {
			return null;
		} catch (InvalidKeyException e) {
			return null;
		}

	}

}
