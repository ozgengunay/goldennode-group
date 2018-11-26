package com.goldennode.commons.util;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class RestClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);
    private static String publicKey;
    private static String secretKey;
    private static String SERVER_URL = "http://localhost:8080/goldennode-cloud-0.0.1-SNAPSHOT";
    
    static {
        publicKey = System.getenv("GN_PK");
        secretKey = System.getenv("GN_SK");
        if (publicKey == null) {
            publicKey = System.getProperty("com.goldennode.client.publicKey");
        }
        if (secretKey == null) {
            secretKey = System.getProperty("com.goldennode.client.secretKey");
        }
        if (publicKey == null) {
            throw new RuntimeException("can't load publicKey");
        }
        if (secretKey == null) {
            throw new RuntimeException("can't load secretKey");
        }
    }

    public static  ResponseEntity<String> call(String uri, HttpMethod method) throws GoldenNodeException {
        return call(uri, method, null);
    }

    public static ResponseEntity<String> call(String uri, HttpMethod method, String body) throws GoldenNodeException {
        RequestEntity<String> entity;
        try {
            entity = new RequestEntity<>(body, method, new URI(SERVER_URL + uri));
            entity = RestClient.signEntity(entity);
            LOGGER.debug("Request=" + entity);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> result = restTemplate.exchange(entity, String.class);
            LOGGER.debug("Response=" + result);
            return result;
        } catch (URISyntaxException e) {
            LOGGER.error("Error occured", e);
            throw new GoldenNodeException(e);
        } catch (RestClientException e) {
            LOGGER.error("Error occured", e);
            throw new GoldenNodeException(e);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static RequestEntity signEntity(RequestEntity entity) {
        String auth = "key=" + publicKey + ",timestamp=" + System.currentTimeMillis() / 1000L + ",nonce=" + UUID.getUUID();
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> map = entity.getHeaders();
        Iterator<Entry<String, List<String>>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, List<String>> entry = iter.next();
            List<String> list = entry.getValue();
            Iterator<String> iter2 = list.iterator();
            while (iter2.hasNext()) {
                String singleValue = iter2.next();
                headers.add(entry.getKey(), singleValue);
            }
        }
        headers.add("Signature", generateHmacSHA256Signature(auth + entity.getUrl().getPath() + (entity.getBody() == null ? "" : entity.getBody()) + entity.getMethod(), secretKey));
        LOGGER.debug("Signature to be created from : " + auth + entity.getUrl().getPath() + (entity.getBody() == null ? "" : entity.getBody()) + entity.getMethod());
        headers.add("Authorization", auth);
        return new RequestEntity(entity.getBody(), headers, entity.getMethod(), entity.getUrl());
    }

    private static String generateHmacSHA256Signature(String data, String secretKey) {
        byte[] hmacData = null;
        try {
            SecretKeySpec sKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(sKey);
            hmacData = mac.doFinal(data.getBytes("UTF-8"));
            return new String(Base64.encode(hmacData), "UTF-8");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeyException e) {
            LOGGER.error("Error occured", e);
            return null;
        }
    }
}
