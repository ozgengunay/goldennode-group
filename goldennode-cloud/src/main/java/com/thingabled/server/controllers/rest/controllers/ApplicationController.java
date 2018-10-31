package com.thingabled.server.controllers.rest.controllers;

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

import com.thingabled.commons.entity.Application;
import com.thingabled.commons.entity.BaseEntity.Status;
import com.thingabled.commons.repository.ApplicationRepository;
import com.thingabled.server.controllers.rest.ErrorCode;
import com.thingabled.server.controllers.rest.ThingabledRestException;
import com.thingabled.server.security.ThingabledUserDetails;

@RestController
@RequestMapping(value = { "/rest/applications" })
@CrossOrigin(origins="*")
public class ApplicationController {
	
	@Autowired
	private ApplicationRepository repository;

	@RequestMapping(value = { "/{applicationId}" }, method = { RequestMethod.GET })
	public Application get(Principal principal, @PathVariable("applicationId") String applicationId)
			throws ThingabledRestException {
		ThingabledUserDetails userDetails = (ThingabledUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Application entity = repository.findByIdAndUserIdAndStatus(applicationId, userDetails.getId(), Status.ENABLED);
		if (entity == null) {
			throw new ThingabledRestException(ErrorCode.ENTITY_NOT_FOUND);
		}
		return entity;
	}

	@RequestMapping(method = { RequestMethod.GET })
	public List<Application> getAll(Principal principal) {
		ThingabledUserDetails userDetails = (ThingabledUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		return repository.findByUserIdAndStatus(userDetails.getId(), Status.ENABLED);
	}

	@RequestMapping(method = { RequestMethod.POST })
	public void add(Principal principal, HttpServletRequest request, HttpServletResponse response,
			@RequestBody Application data) {
		ThingabledUserDetails userDetails = (ThingabledUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Application newEntity = Application.newEntity();
		newEntity.setName(data.getName());
		newEntity.setUserId(userDetails.getId());
		newEntity = repository.save(newEntity);
		response.setHeader("Location", request.getRequestURI() + newEntity.getId());
	}

	@RequestMapping(value = { "/{applicationId}" }, method = { RequestMethod.PUT })
	public Application update(Principal principal, @PathVariable("applicationId") String applicationId,
			@RequestBody Application data) throws ThingabledRestException {
		ThingabledUserDetails userDetails = (ThingabledUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Application entity = repository.findByIdAndUserIdAndStatus(applicationId, userDetails.getId(), Status.ENABLED);
		if (entity == null) {
			throw new ThingabledRestException(ErrorCode.ENTITY_NOT_FOUND);
		}
		entity.setName(data.getName());
		return repository.save(entity);
	}

	@RequestMapping(value = { "/{applicationId}" }, method = { RequestMethod.DELETE })
	public void delete(Principal principal, @PathVariable("applicationId") String applicationId)
			throws ThingabledRestException {
		ThingabledUserDetails userDetails = (ThingabledUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Application entity = repository.findByIdAndUserIdAndStatus(applicationId, userDetails.getId(), Status.ENABLED);
		if (entity == null) {
			throw new ThingabledRestException(ErrorCode.ENTITY_NOT_FOUND);
		}
		entity.disable();

		repository.save(entity);
	}

}
