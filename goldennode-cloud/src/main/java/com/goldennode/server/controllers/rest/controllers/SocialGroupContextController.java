package com.goldennode.server.controllers.rest.controllers;

import java.security.Principal;

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

import com.thingabled.commons.entity.BaseEntity.Status;
import com.thingabled.commons.entity.SocialGroup;
import com.thingabled.commons.entity.SocialGroupContext;
import com.thingabled.commons.repository.SocialGroupContextRepository;
import com.thingabled.commons.repository.SocialGroupRepository;
import com.goldennode.server.controllers.rest.ErrorCode;
import com.goldennode.server.controllers.rest.ThingabledRestException;
import com.goldennode.server.security.ThingabledUserDetails;

@RestController
@RequestMapping(value = { "/rest/socialgroupcontext" })
@CrossOrigin(origins="*")
public class SocialGroupContextController {
	@Autowired
	private SocialGroupContextRepository repository;
	@Autowired
	private SocialGroupRepository groupRepository;

	@RequestMapping(value = { "/{socialGroupContextId}" }, method = { RequestMethod.GET })
	public SocialGroupContext get(Principal principal, @PathVariable("socialGroupContextId") String socialGroupContextId)
			throws ThingabledRestException {
		ThingabledUserDetails userDetails = (ThingabledUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		SocialGroupContext groupMember = repository.findByIdAndStatus(socialGroupContextId, Status.ENABLED);
		if (groupMember == null) {
			throw new ThingabledRestException(ErrorCode.ENTITY_NOT_FOUND);
		}

		// For checking that group is user's
		SocialGroup group = groupRepository.findByIdAndUserIdAndStatus(groupMember.getSocialGroupId(),
				userDetails.getId(), Status.ENABLED);
		if (group == null) {
			throw new ThingabledRestException(ErrorCode.ENTITY_NOT_FOUND);
		}

		return groupMember;
	}

	@RequestMapping(method = { RequestMethod.POST })
	public void add(Principal principal, HttpServletRequest request, HttpServletResponse response,
			@RequestBody SocialGroupContext data) throws ThingabledRestException {
		ThingabledUserDetails userDetails = (ThingabledUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		// For checking that group is user's
		SocialGroup group = groupRepository.findByIdAndUserIdAndStatus(data.getSocialGroupId(), userDetails.getId(),
				Status.ENABLED);
		if (group == null) {
			throw new ThingabledRestException(ErrorCode.ENTITY_NOT_FOUND);
		}
		SocialGroupContext newEntity = SocialGroupContext.newEntity();
		newEntity.setSocialGroupId(group.getId());
		newEntity.setThingContextId(data.getThingContextId());
		newEntity = repository.save(newEntity);
		response.setHeader("Location", request.getRequestURI() + newEntity.getId());
	}

	@RequestMapping(value = { "/{socialGroupContextId}" }, method = { RequestMethod.DELETE })
	public void delete(Principal principal, @PathVariable("socialGroupContextId") String socialGroupContextId)
			throws ThingabledRestException {
		ThingabledUserDetails userDetails = (ThingabledUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		SocialGroupContext entity = repository.findByIdAndStatus(socialGroupContextId, Status.ENABLED);
		if (entity == null) {
			throw new ThingabledRestException(ErrorCode.ENTITY_NOT_FOUND);
		}
		// For checking that group is user's
		SocialGroup group = groupRepository.findByIdAndUserIdAndStatus(entity.getSocialGroupId(), userDetails.getId(),
				Status.ENABLED);
		if (group == null) {
			throw new ThingabledRestException(ErrorCode.ENTITY_NOT_FOUND);
		}

		entity.disable();

		repository.save(entity);
	}

}
