package com.goldennode.server;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ControllerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerTest.class);
    private static String SERVER_URL = "http://localhost:8080/goldennode-cloud-0.0.1-SNAPSHOT";

    /*@Test
    public void _01_put() throws URISyntaxException {
        String body = UUID.randomUUID().toString();
        String uri = "/goldennode/map/id/1/put/key/1";
        RequestEntity<String> entity = new RequestEntity<>(body, HttpMethod.POST, new URI(SERVER_URL + uri));
        entity = Authenticator.signEntity(entity);
        LOGGER.debug("Request=" + entity);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.exchange(entity, String.class);
        LOGGER.debug("Response=" + result);
    }
    @Test
    public void _01_put2() throws URISyntaxException {
        String body = UUID.randomUUID().toString();
        String uri = "/goldennode/map/id/1/put/key/2";
        RequestEntity<String> entity = new RequestEntity<>(body, HttpMethod.POST, new URI(SERVER_URL + uri));
        entity = Authenticator.signEntity(entity);
        LOGGER.debug("Request=" + entity);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.exchange(entity, String.class);
        LOGGER.debug("Response=" + result);
    }
    @Test
    public void _02_get() throws URISyntaxException {
        String body = "";
        String uri = "/goldennode/map/id/1/get/key/1";
        RequestEntity<String> entity = new RequestEntity<>(body, HttpMethod.GET, new URI(SERVER_URL + uri));
        entity = Authenticator.signEntity(entity);
        LOGGER.debug("Request=" + entity);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.exchange(entity, String.class);
        LOGGER.debug("Response=" + result);
    }*/
    @Test
    public void _03_keySet() throws URISyntaxException {
        String body = "";
        String uri = "/goldennode/map/id/1/keySet";
        RequestEntity<String> entity = new RequestEntity<>(body, HttpMethod.GET, new URI(SERVER_URL + uri));
        entity = Authenticator.signEntity(entity);
        LOGGER.debug("Request=" + entity);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.exchange(entity, String.class);
        LOGGER.debug("Response=" + result.getBody());
    }
}
