package com.goldennode.server;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.client.RestTemplate;

import com.goldennode.commons.util.UUID;

public class ClientAuthenticationSampleGet {
    private static String base_url = "http://localhost:8080";

    @Test
    public void sampleRequest() throws GeneralSecurityException {
        String uri = "/goldennode-cloud-0.0.1-SNAPSHOT/goldennode/map/id/1/get/key/1";
        String body = "";
        System.out.println("body > " + body);
        String publicKey = "ogunay1978@gmail.com";
        String secretKey = "12345678";
        String auth = "key=" + publicKey + ",timestamp=" + System.currentTimeMillis() / 1000L + ",nonce=" + UUID.getUUID();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Signature", generateHmacSHA256Signature(auth + uri + body, secretKey));
        System.out.println("Signature to be generated : " + generateHmacSHA256Signature(auth + uri + body, secretKey));
        headers.add("Authorization", auth);
        headers.add("Content-Type", "application/json");
        System.out.println("Signature=" + headers.get("Signature"));
        System.out.println("Authorization=" + headers.get("Authorization"));
        HttpEntity<String> entity = new HttpEntity<String>(body, headers);
        System.out.println("Request=" + entity.toString());
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.exchange(base_url + uri, HttpMethod.GET, entity, String.class);
        System.out.println(result);
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
