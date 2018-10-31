package com.thingabled.server.controllers.rest.tomtom.ifttt;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thingabled.server.controllers.rest.ThingabledRestException;

@RestController
@RequestMapping(value = { "/tomtom/ifttt/v1/status" })
@CrossOrigin(origins="*")
public class ChannelStatus {
	
	@RequestMapping(method =  RequestMethod.GET, headers={"Accept=application/json"})
	public ResponseEntity<Object> getStatus(HttpServletRequest request)
			throws ThingabledRestException {
		if (request.getHeader("IFTTT-Channel-Key")==null ||!request.getHeader("IFTTT-Channel-Key").equals(request.getServletContext().getInitParameter("iftttKey"))){
			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		} 
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
}
