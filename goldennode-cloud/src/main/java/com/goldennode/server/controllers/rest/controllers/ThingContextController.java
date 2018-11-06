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
import com.goldennode.commons.entity.ThingContext;
import com.goldennode.commons.entity.BaseEntity.Status;
import com.goldennode.commons.repository.ThingContextRepository;
import com.goldennode.server.controllers.rest.ErrorCode;
import com.goldennode.server.controllers.rest.GoldenNodeRestException;
import com.goldennode.server.security.GoldenNodeUserDetails;

@RestController
@RequestMapping(value = { "/rest/thingcontext" })
@CrossOrigin(origins = "*")
public class ThingContextController {
	@Autowired
	private ThingContextRepository repository;

	@RequestMapping(value = { "/{thingContextId}" }, method = { RequestMethod.GET })
	public ThingContext get(Principal principal, @PathVariable("thingContextId") String thingContextId) {
		return repository.findByIdAndStatus(thingContextId,Status.ENABLED);
	}

	@RequestMapping(method = { RequestMethod.GET })
	public List<ThingContext> getAll(Principal principal) {
		return repository.findAll();
	}

	@RequestMapping(method = { RequestMethod.POST })
	public void add(Principal principal, HttpServletRequest request, HttpServletResponse response,
			@RequestBody ThingContext data) {
		GoldenNodeUserDetails userDetails = (GoldenNodeUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		ThingContext newEntity = ThingContext.newEntity();
		newEntity.setName(data.getName());
		newEntity.setParent(data.getParent());
		newEntity.setType(data.getType());
		newEntity.setUserId(userDetails.getId());
		newEntity = repository.save(newEntity);
		response.setHeader("Location", request.getRequestURI() + newEntity.getId());
	}

	
	@RequestMapping(value = { "/{thingContextId}" }, method = { RequestMethod.PUT })
	public ThingContext update(Principal principal, @PathVariable("thingContextId") String thingContextId,
			@RequestBody ThingContext data) throws GoldenNodeRestException {
		GoldenNodeUserDetails userDetails = (GoldenNodeUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		ThingContext entity = repository.findByIdAndUserIdAndStatus(thingContextId,userDetails.getId(),Status.ENABLED);
		if (entity == null) {
			throw new GoldenNodeRestException(ErrorCode.THING_CONTEXT_NOT_FOUND);
		}
	
		entity.setName(data.getName());
		entity.setParent(data.getParent());
		entity.setType(data.getType());

		return repository.save(entity);
	}

	@RequestMapping(value = { "/{thingContextId}" }, method = { RequestMethod.DELETE })
	public void delete(Principal principal, @PathVariable("thingContextId") String thingContextId)
			throws GoldenNodeRestException {
		GoldenNodeUserDetails userDetails = (GoldenNodeUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		ThingContext entity = repository.findByIdAndUserIdAndStatus(thingContextId,userDetails.getId(),Status.ENABLED);
		if (entity == null) {
			throw new GoldenNodeRestException(ErrorCode.THING_CONTEXT_NOT_FOUND);
		}
		entity.disable();
		repository.save(entity);
	}

}
