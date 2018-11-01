package com.goldennode.server.controllers.rest.tomtom.ifttt;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thingabled.commons.entity.Users;
import com.thingabled.commons.repository.UserRepository;

@RestController
@RequestMapping(value = { "/tomtom/ifttt/v1/user" })
@CrossOrigin(origins="*")
public class UserInfo {
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = { "/info" }, method =  RequestMethod.GET,produces = "application/json; charset=UTF-8" )
	public ResponseEntity<String> getInfo()
			throws IFTTTRestException {
		
		User user=(User)SecurityContextHolder.getContext()
		.getAuthentication().getPrincipal();
		Users usr = userRepository.findByEmail(user.getUsername());
		if (usr == null) {
			throw new IFTTTRestException("User not found");
		}
		JSONObject json=new JSONObject();
		JSONObject jdata=new JSONObject();
		jdata.put("id",user.getUsername() );
		jdata.put("name",usr.getFirstName() +" " +usr.getLastName() );
		json.put("data", jdata);
		return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
	}
	
	
	
	
	
}
