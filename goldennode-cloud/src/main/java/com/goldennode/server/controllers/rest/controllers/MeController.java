package com.goldennode.server.controllers.rest.controllers;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.goldennode.commons.entity.Users;
import com.goldennode.commons.repository.UserRepository;
import com.goldennode.server.controllers.rest.ErrorCode;
import com.goldennode.server.controllers.rest.GoldenNodeRestException;
import com.goldennode.server.security.GoldenNodeUserDetails;

@RestController
@RequestMapping(value = { "/rest/me" })
@CrossOrigin(origins = "*")
public class MeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MeController.class);

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(method = { RequestMethod.PUT })
	public void setOAuthPassword(Principal principal, @RequestBody Users data) throws GoldenNodeRestException {
		GoldenNodeUserDetails userDetails = (GoldenNodeUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Users entity = userRepository.findById(userDetails.getId());
		if (entity == null) {
			throw new GoldenNodeRestException(ErrorCode.ENTITY_NOT_FOUND);
		}
		if (data.getPassword() == null) {
			entity.setPassword(null);
		} else {
			BCryptPasswordEncoder enc = new BCryptPasswordEncoder(10);
			entity.setPassword(enc.encode(data.getPassword()));
		}
		userRepository.save(entity);
	}

	@RequestMapping(method = { RequestMethod.GET })
	public Users getMe(Principal principal) throws GoldenNodeRestException {
		GoldenNodeUserDetails userDetails = (GoldenNodeUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Users entity = userRepository.findById(userDetails.getId());
		if (entity == null) {
			throw new GoldenNodeRestException(ErrorCode.ENTITY_NOT_FOUND);
		}

		if (entity.getPassword() != null)
			entity.setPassword("OAUTH SET");
		else {
			entity.setPassword("OAUTH NOT SET");
		}
		if (entity.getPassword_() != null)
			entity.setPassword_("SET");
		else
			entity.setPassword_("NOT SET");

		return entity;
	}

}
