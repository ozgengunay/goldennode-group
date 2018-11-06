package com.goldennode.server.controllers.rest.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.goldennode.commons.entity.SocialGroup;
import com.goldennode.commons.entity.BaseEntity.Status;
import com.goldennode.commons.repository.SocialGroupRepository;
import com.goldennode.server.controllers.rest.ErrorCode;
import com.goldennode.server.controllers.rest.GoldenNodeRestException;
import com.goldennode.server.security.GoldenNodeUserDetails;

@RestController
@RequestMapping(value = { "/rest/socialgroups" })
@CrossOrigin(origins="*")
public class SocialGroupController {
	@Autowired
	private SocialGroupRepository repository;

	@RequestMapping(value = { "/{socialGroupId}" }, method = { RequestMethod.GET })
	public SocialGroup get(Principal principal, @PathVariable("socialGroupId") String socialGroupId)
			throws GoldenNodeRestException {
		GoldenNodeUserDetails userDetails = (GoldenNodeUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		SocialGroup entity = repository.findByIdAndUserIdAndStatus(socialGroupId, userDetails.getId(), Status.ENABLED);
		if (entity == null) {
			throw new GoldenNodeRestException(ErrorCode.ENTITY_NOT_FOUND);
		}
		return entity;
	}

	@RequestMapping(method = { RequestMethod.GET })
	public List<SocialGroup> getAll(Principal principal) {
		GoldenNodeUserDetails userDetails = (GoldenNodeUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		return repository.findByUserIdAndStatus(userDetails.getId(), Status.ENABLED);
	}

	@RequestMapping(method = { RequestMethod.POST })
	public void add(Principal principal, HttpServletRequest request, HttpServletResponse response, @RequestBody SocialGroup data) {
		GoldenNodeUserDetails userDetails = (GoldenNodeUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		SocialGroup newEntity = SocialGroup.newEntity();
		newEntity.setName(data.getName());
		newEntity.setUserId(userDetails.getId());
		newEntity = repository.save(newEntity);
		response.setHeader("Location", request.getRequestURI()  + newEntity.getId());
	}
	
	@RequestMapping(value = { "/{socialGroupId}" }, method = { RequestMethod.PUT })
	public SocialGroup update(Principal principal, @PathVariable("socialGroupId") String socialGroupId,
			@RequestBody SocialGroup data) throws GoldenNodeRestException {
		GoldenNodeUserDetails userDetails = (GoldenNodeUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		SocialGroup entity = repository.findByIdAndUserIdAndStatus(socialGroupId, userDetails.getId(), Status.ENABLED);
		if (entity == null) {
			throw new GoldenNodeRestException(ErrorCode.ENTITY_NOT_FOUND);
		}
		entity.setName(data.getName());
		return repository.save(entity);
	}

	@RequestMapping(value = { "/{socialGroupId}" }, method = { RequestMethod.DELETE })
	public void delete(Principal principal, @PathVariable("socialGroupId") String socialGroupId)
			throws GoldenNodeRestException {
		GoldenNodeUserDetails userDetails = (GoldenNodeUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		SocialGroup entity = repository.findByIdAndUserIdAndStatus(socialGroupId, userDetails.getId(), Status.ENABLED);
		if (entity == null) {
			throw new GoldenNodeRestException(ErrorCode.ENTITY_NOT_FOUND);
		}
		entity.disable();

		repository.save(entity);
	}

}
