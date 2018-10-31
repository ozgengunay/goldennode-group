package com.thingabled.server.controllers.rest.controllers;

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
import com.thingabled.commons.entity.SocialGroupMember;
import com.thingabled.commons.repository.SocialGroupMemberRepository;
import com.thingabled.commons.repository.SocialGroupRepository;
import com.thingabled.server.controllers.rest.ErrorCode;
import com.thingabled.server.controllers.rest.ThingabledRestException;
import com.thingabled.server.security.ThingabledUserDetails;

@RestController
@RequestMapping(value = { "/rest/socialgroupmembers" })
@CrossOrigin(origins="*")
public class SocialGroupMemberController {
	@Autowired
	private SocialGroupMemberRepository repository;
	@Autowired
	private SocialGroupRepository groupRepository;

	@RequestMapping(value = { "/{socialGroupMemberId}" }, method = { RequestMethod.GET })
	public SocialGroupMember get(Principal principal, @PathVariable("socialGroupMemberId") String socialGroupMemberId)
			throws ThingabledRestException {
		ThingabledUserDetails userDetails = (ThingabledUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		SocialGroupMember groupMember = repository.findByIdAndStatus(socialGroupMemberId, Status.ENABLED);
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
			@RequestBody SocialGroupMember data) throws ThingabledRestException {
		ThingabledUserDetails userDetails = (ThingabledUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		// For checking that group is user's
		SocialGroup group = groupRepository.findByIdAndUserIdAndStatus(data.getSocialGroupId(), userDetails.getId(),
				Status.ENABLED);
		if (group == null) {
			throw new ThingabledRestException(ErrorCode.ENTITY_NOT_FOUND);
		}
		SocialGroupMember newEntity = SocialGroupMember.newEntity();
		newEntity.setSocialGroupId(group.getId());
		newEntity.setUserIdMember(data.getUserIdMember());
		newEntity = repository.save(newEntity);
		response.setHeader("Location", request.getRequestURI() + newEntity.getId());
	}

	@RequestMapping(value = { "/{socialGroupMemberId}" }, method = { RequestMethod.DELETE })
	public void delete(Principal principal, @PathVariable("socialGroupMemberId") String socialGroupMemberId)
			throws ThingabledRestException {
		ThingabledUserDetails userDetails = (ThingabledUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		SocialGroupMember entity = repository.findByIdAndStatus(socialGroupMemberId, Status.ENABLED);
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
