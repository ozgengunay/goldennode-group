package com.goldennode.server;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ControllerTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerTest.class);
	private static String URI = "/rest/thing";
	private String location;
//	
//	@Test
//	public void add() throws URISyntaxException {
//		Location loc=new Location();
//		loc.setLatitude(Math.random());
//		loc.setLongitude(Math.random());
//		loc.setName("Test Location"+new Date());
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		RequestEntity entity = new RequestEntity(new JSONObject(loc).toString(), headers, HttpMethod.POST, new URI((Config.SERVER_URL+"/"+Config.CONTEXT_PATH+ URI)));
//		entity = Authenticator.signRequest(entity);
//		LOGGER.debug("Request=" + entity);
//		RestTemplate restTemplate = new RestTemplate();
//		ResponseEntity<String> result = restTemplate.exchange(entity,String.class );
//		location=result.getHeaders().get("Location").get(0);
//		LOGGER.debug("Response=" + result);
//		LOGGER.debug("location=" + location);
//		
//		
//		
//	}
//	
//	@Test
//	public void get() throws URISyntaxException {
//		RequestEntity entity = new RequestEntity(HttpMethod.GET, new URI(Config.SERVER_URL+location));
//		entity = Authenticator.signRequest(entity);
//		LOGGER.debug("Request=" + entity);
//		RestTemplate restTemplate = new RestTemplate();
//		ResponseEntity<Location> result = restTemplate.exchange(entity, Location.class);
//		LOGGER.debug("Response=" + result);
//		
//	}

	//@Test
	public void getAll() throws URISyntaxException {
		String d="TEST";
		RequestEntity entity = new RequestEntity(d,HttpMethod.POST, new URI(Config.SERVER_URL+"/"+Config.CONTEXT_PATH+URI));
		entity = Authenticator.signRequest(entity);
		LOGGER.debug("Request=" + entity);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List> result = restTemplate.exchange(entity, List.class);
		LOGGER.debug("Response=" + result);
	}
//
//	
//	@Test
//	public void modify() throws URISyntaxException{
//		Location loc=new Location();
//		loc.setLatitude(Math.random());
//		loc.setLongitude(Math.random());
//		loc.setName("Test Location"+new Date());
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		RequestEntity entity = new RequestEntity(loc, headers, HttpMethod.GET, new URI(Config.SERVER_URL+location));
//		entity = Authenticator.signRequest(entity);
//		LOGGER.debug("Request=" + entity);
//		RestTemplate restTemplate = new RestTemplate();
//		ResponseEntity<String> result = restTemplate.exchange(entity, String.class);
//		location=result.getHeaders().get("Location").get(0);
//		LOGGER.debug("Response=" + result);
//		LOGGER.debug("location=" + location);
//	}
//	@Test
//	public void delete() throws URISyntaxException{
//		ss.deleteLocation(locationId);
//	}

}
