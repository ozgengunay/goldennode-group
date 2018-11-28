package com.goldennode.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

public class TestRestClient {
    
    private static String SERVER_URL = "http://localhost:8080";

    /*@Test
    public void _01_put() {
        try {
            ResponseEntity entity = RestClient.call("/goldennode/map/id/1/put/key/1", "POST", "{\"TEST\"}");
            System.out.println("->" + entity.responseCode + " " + entity.body);
        } catch (GoldenNodeException e) {
            e.printStackTrace();
        }
    }
*/
    
    
  //  @Test
    public void _02_get() {
        try {
            ResponseEntity entity = RestClient.call("/goldennode/map/id/1/get/key/1", "GET");
            System.out.println("->" + entity.responseCode + " " + entity.body);
        } catch (GoldenNodeException e) {
            e.printStackTrace();
        }
    }
    
    
    
    @Test
    public void _01_put2() throws URISyntaxException {
        String body = UUID.randomUUID().toString();
        String uri = "/goldennode/map/id/1/put/key/2";
        RequestEntity<String> entity = new RequestEntity<>(body, HttpMethod.POST, new URI(SERVER_URL + uri));
        entity = Authenticator.signEntity(entity);
       // LOGGER.debug("Request=" + entity);
        RestTemplate restTemplate = new RestTemplate();
        org.springframework.http.ResponseEntity<String> result = restTemplate.exchange(entity, String.class);
     //   LOGGER.debug("Response=" + result);
    }
}
