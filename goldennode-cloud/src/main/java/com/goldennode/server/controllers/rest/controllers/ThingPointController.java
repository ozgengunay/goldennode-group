package com.goldennode.server.controllers.rest.controllers;

import java.security.Principal;
import java.util.ArrayList;
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

import com.thingabled.commons.entity.BaseEntity.Status;
import com.thingabled.commons.entity.Thing;
import com.thingabled.commons.entity.ThingContext;
import com.thingabled.commons.entity.ThingContext.Type;
import com.thingabled.commons.entity.ThingOwnership;
import com.thingabled.commons.entity.ThingPoint;
import com.thingabled.commons.entity.ThingPoint.Permission;
import com.thingabled.commons.repository.ThingContextRepository;
import com.thingabled.commons.repository.ThingOwnershipRepository;
import com.thingabled.commons.repository.ThingPointRepository;
import com.thingabled.commons.repository.ThingRepository;
import com.goldennode.server.controllers.rest.ErrorCode;
import com.goldennode.server.controllers.rest.ThingabledRestException;
import com.goldennode.server.security.ThingabledUserDetails;

@RestController
@RequestMapping(value = { "/rest/thingpoints" })
@CrossOrigin(origins = "*")
public class ThingPointController {
	
	@Autowired
	private ThingRepository repository;
	@Autowired
	private ThingOwnershipRepository thingOwnershipRepository;
	@Autowired
	private ThingPointRepository thingPointRepository;
	@Autowired
	private ThingContextRepository thingContextRepository;

	@RequestMapping(value = { "/{thingPointId}" }, method = { RequestMethod.GET })
	public ThingPoint get(Principal principal, @PathVariable("thingPointId") String thingPointId)
			throws ThingabledRestException {
		ThingabledUserDetails userDetails = (ThingabledUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		ThingPoint data = thingPointRepository.findByIdAndStatus(thingPointId, Status.ENABLED);
		if (data == null) {
			throw new ThingabledRestException(ErrorCode.THING_POINT_NOT_FOUND);
		}
		// get owner
		ThingOwnership ownership = thingOwnershipRepository.findByThingIdAndUserIdAndStatus(
				data.getThingId(), userDetails.getId(), Status.ENABLED);
		if (ownership == null) {
			throw new ThingabledRestException(ErrorCode.THING_NOT_OWNED);
		}

		return data;
	}

	@RequestMapping(method = { RequestMethod.POST })
	public List<ThingPoint> addPoint(Principal principal, HttpServletRequest request, HttpServletResponse response,
			@RequestBody List<ThingPoint> data) throws ThingabledRestException {

		List<ThingPoint> newEntities = new ArrayList<ThingPoint>();
		for (ThingPoint dat : data) {
			ThingPoint point = addPoint(principal, request, response, dat);
			newEntities.add(point);
		}
		return newEntities;
	}

	private ThingPoint addPoint(Principal principal, HttpServletRequest request, HttpServletResponse response,
			ThingPoint data) throws ThingabledRestException {

		ThingabledUserDetails userDetails = (ThingabledUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		// Start Checks
		Thing entity = repository.findByIdAndStatus(data.getThingId(), Status.ENABLED);
		if (entity == null) {
			throw new ThingabledRestException(ErrorCode.THING_NOT_FOUND);
		}

		ThingContext context = thingContextRepository.findByIdAndStatus(entity.getThingContextId(), Status.ENABLED);
		if (context == null) {
			throw new ThingabledRestException(ErrorCode.THING_CONTEXT_NOT_FOUND);
		}

		if (context.getType() == Type.PHYSICAL) {
			throw new ThingabledRestException(ErrorCode.CAN_NOT_ADD_POINT_TO_PHYSICAL_THINGS);
		}
		ThingOwnership ownership_ = thingOwnershipRepository.findByThingIdAndUserIdAndStatus(
				entity.getId(), userDetails.getId(),  Status.ENABLED);
		if (ownership_==null) {
			throw new ThingabledRestException(ErrorCode.THING_NOT_OWNED);
		}
		ThingPoint thingPoint = thingPointRepository.findByThingIdAndInternalIdAndStatus(entity.getId(),
				data.getInternalId(), Status.ENABLED);
		if (thingPoint != null) {
			throw new ThingabledRestException(ErrorCode.THING_POINT_ALREADY_EXISTS);
		}
		// End Checks

		ThingPoint newSubentity = ThingPoint.newEntity();
		newSubentity.setInternalId(data.getInternalId());
		newSubentity.setName(data.getName());
		newSubentity.setType(data.getType());
		newSubentity.setUnit(data.getUnit());
		newSubentity.setPermission(Permission.FRIENDS);//TODO set to private by default

		entity.addThingPoint(newSubentity);
		repository.save(entity);

		response.setHeader("Location", request.getRequestURI() + newSubentity.getId());
		return newSubentity;
	}

	@RequestMapping(value = { "/{thingPointId}" }, method = { RequestMethod.DELETE })
	public void removePoint(Principal principal, @PathVariable("thingPointId") String thingPointId,
			HttpServletResponse response) throws ThingabledRestException {
		ThingabledUserDetails userDetails = (ThingabledUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		// Start Checks
		ThingPoint thingPointEntity = thingPointRepository.findByIdAndStatus(thingPointId, Status.ENABLED);
		if (thingPointEntity == null) {
			throw new ThingabledRestException(ErrorCode.THING_POINT_NOT_FOUND);
		}

		ThingContext context = thingContextRepository.findByIdAndStatus(thingPointEntity.getThing().getThingContextId(),
				Status.ENABLED);
		if (context == null) {
			throw new ThingabledRestException(ErrorCode.THING_CONTEXT_NOT_FOUND);
		}

		if (context.getType() == Type.PHYSICAL) {
			throw new ThingabledRestException(ErrorCode.CAN_NOT_REMOVE_POINT_FROM_PHYSICAL_THINGS);
		}

		// get owner
		ThingOwnership ownership = thingOwnershipRepository.findByThingIdAndUserIdAndStatus(
				thingPointEntity.getThingId(), userDetails.getId(),  Status.ENABLED);
		if (ownership == null) {
			throw new ThingabledRestException(ErrorCode.THING_NOT_OWNED);
		}
		// End Checks
		thingPointEntity.disable();
		thingPointRepository.save(thingPointEntity);

	}
}
