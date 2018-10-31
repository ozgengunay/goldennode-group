package com.thingabled.server.controllers.rest.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thingabled.commons.entity.BaseEntity.Status;
import com.thingabled.commons.entity.Thing;
import com.thingabled.commons.entity.ThingContext;
import com.thingabled.commons.entity.ThingContext.Type;
import com.thingabled.commons.entity.ThingOwnership;
import com.thingabled.commons.entity.ThingPoint;
import com.thingabled.commons.entity.ThingPoint.Permission;
import com.thingabled.commons.entity.Users;
import com.thingabled.commons.repository.ThingContextRepository;
import com.thingabled.commons.repository.ThingOwnershipRepository;
import com.thingabled.commons.repository.ThingRepository;
import com.thingabled.commons.util.UUID;
import com.thingabled.server.controllers.rest.ErrorCode;
import com.thingabled.server.controllers.rest.ThingabledRestException;
import com.thingabled.server.security.ThingabledUserDetails;

@RestController
@RequestMapping(value = { "/rest/things" })
@CrossOrigin(origins = "*")
public class ThingController {

	@Autowired
	private ThingRepository thingRepository;
	@Autowired
	private ThingOwnershipRepository thingOwnershipRepository;
	@Autowired
	private ThingContextRepository thingContextRepository;

	
	@RequestMapping(value = { "/{id}" }, method = { RequestMethod.GET })
	public Thing get(@PathVariable("id") String id, @RequestParam("idType") String idType)
			throws ThingabledRestException {
		ThingabledUserDetails userDetails = (ThingabledUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		Thing entity = null;
		if (idType.equals("thingId")) {
			entity = thingRepository.findByIdAndStatus(id, Status.ENABLED);

		}
		if (idType.equals("publicKey")) {
			entity = thingRepository.findByPublickeyAndStatus(id, Status.ENABLED);
		}
		if (idType.equals("thingContextId")) {
			return findByThingContextId(userDetails, id);
		}

		if (entity == null) {
			throw new ThingabledRestException(ErrorCode.THING_NOT_FOUND);
		}
		// get owner
		ThingOwnership ownership = thingOwnershipRepository.findByThingIdAndUserIdAndStatus(id, userDetails.getId(),
				Status.ENABLED);
		if (ownership == null) {
			throw new ThingabledRestException(ErrorCode.THING_NOT_OWNED);
		}

		return entity;
	}

	private Thing findByThingContextId(ThingabledUserDetails userDetails, String id) throws ThingabledRestException {
		List<ThingOwnership> ownerships = thingOwnershipRepository.findByUserIdAndStatus(userDetails.getId(),
				Status.ENABLED);
		for (ThingOwnership ownership : ownerships) {
			Thing thing = thingRepository.findByIdAndStatus(ownership.getThingId(), Status.ENABLED);
			if (thing.getThingContextId().equals(id)) {
				return thing;
			}
		}
		throw new ThingabledRestException(ErrorCode.THING_NOT_FOUND);

	}

	
	@RequestMapping(method = { RequestMethod.GET })
	public List<Thing> get(Principal principal) throws ThingabledRestException {
		ThingabledUserDetails userDetails = (ThingabledUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		// get owner
		List<ThingOwnership> ownerships = thingOwnershipRepository.findByUserIdAndStatus(userDetails.getId(),
				Status.ENABLED);
		List<Thing> list = new ArrayList<Thing>();
		for (ThingOwnership ownership : ownerships) {
			list.add(thingRepository.findByIdAndStatus(ownership.getThingId(), Status.ENABLED));
		}

		return list;
	}

	@RequestMapping(method = { RequestMethod.POST })
	public Thing register(HttpServletRequest request, HttpServletResponse response, @RequestBody Thing data)
			throws ThingabledRestException {

		ThingabledUserDetails userDetails = (ThingabledUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		ThingContext context = thingContextRepository.findByIdAndStatus(data.getThingContextId(), Status.ENABLED);
		if (context == null) {
			throw new ThingabledRestException(ErrorCode.THING_CONTEXT_NOT_FOUND);
		}

		if (context.getType() != Type.PHYSICAL && context.getType() != Type.VIRTUAL) {
			throw new ThingabledRestException(ErrorCode.CAN_REGISTER_ONLY_PHYSICAL_OR_VIRTUAL_THINGS);
		}

		if (context.getType() == Type.VIRTUAL) {

			List<ThingOwnership> ownerships = thingOwnershipRepository.findByUserIdAndStatus(userDetails.getId(),
					Status.ENABLED);
			for (ThingOwnership ownership : ownerships) {
				Thing thing = thingRepository.findByIdAndStatus(ownership.getThingId(), Status.ENABLED);
				if (thing.getThingContextId().equals(data.getThingContextId())) {
					throw new ThingabledRestException(ErrorCode.SAME_THING_ALREADY_REGISTERED);
				}
			}
		}

		if (context.getType()==Type.PHYSICAL && !userDetails.getRoles().contains(new SimpleGrantedAuthority(Users.Role.ROLE_DEVELOPER.toString())))
			throw new ThingabledRestException(ErrorCode.REGISTERATIONNOTALLOWEDFORNONDEVELOPERS);
		
		Thing newEntity = Thing.newEntity();
		newEntity.setPublickey(UUID.getUUID());
		newEntity.setSecretkey(UUID.getUUID());
		newEntity.setThingContextId(data.getThingContextId());
		newEntity.setLatitude(data.getLatitude());
		newEntity.setLongitude(data.getLongitude());
		
		List<ThingPoint> points = data.getThingPoints();
		for (ThingPoint point : points) {
			ThingPoint newSubentity = ThingPoint.newEntity();
			newSubentity.setInternalId(context.getType() == Type.VIRTUAL ? point.getInternalId() : null);
			newSubentity.setName(point.getName());
			newSubentity.setType(point.getType());
			newSubentity.setUnit(point.getUnit());
			newSubentity.setPermission(Permission.FRIENDS);// TODO set to
															// private by
															// default
			newEntity.addThingPoint(newSubentity);
		}
		newEntity = thingRepository.save(newEntity);

		if (context.getType() == Type.VIRTUAL) {
			try {
				own(newEntity);
			} catch (ThingabledRestException e) {
				// will not throw
			}
		}
		response.setHeader("Location", request.getRequestURI() + newEntity.getId());
		return newEntity;

	}
	
	
	@RequestMapping(value = { "/{id}" }, method = { RequestMethod.DELETE })
	public void delete(HttpServletResponse response, @PathVariable("id") String id,
			@RequestParam("idType") String idType, @RequestParam("operation") String operation)
					throws ThingabledRestException {

		if (operation.equals("deregister")) {
			deregister(response, id, idType, operation);
		}
		if (operation.equals("disown")) {
			disown(response, id, idType, operation);
		}
	}

	private void deregister(HttpServletResponse response, String id, String idType, String operation)
			throws ThingabledRestException {

		ThingabledUserDetails userDetails = (ThingabledUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		Thing entity = null;
		if (idType.equals("thingId")) {
			entity = thingRepository.findByIdAndStatus(id, Status.ENABLED);

		}
		if (idType.equals("publicKey")) {
			entity = thingRepository.findByPublickeyAndStatus(id, Status.ENABLED);
		}

		if (entity == null) {
			throw new ThingabledRestException(ErrorCode.THING_NOT_FOUND);
		}

		ThingContext context = thingContextRepository.findByIdAndStatus(entity.getThingContextId(), Status.ENABLED);
		if (context == null) {
			throw new ThingabledRestException(ErrorCode.THING_CONTEXT_NOT_FOUND);
		}

		if (context.getType() == Type.PHYSICAL) {
			throw new ThingabledRestException(ErrorCode.CANT_DEREGISTER_PHYSICAL_THINGS);
		}

		ThingOwnership ownership = thingOwnershipRepository.findByThingIdAndUserIdAndStatus(id, userDetails.getId(),
				Status.ENABLED);
		if (ownership == null) {
			throw new ThingabledRestException(ErrorCode.THING_NOT_OWNED);
		}
		ownership.disable();
		thingOwnershipRepository.save(ownership);

		entity.disable();
		List<ThingPoint> points = entity.getThingPoints();
		for (ThingPoint point : points) {
			point.disable();
		}
		thingRepository.save(entity);

	}

	
	
	@RequestMapping(value = { "/{id}" }, method = { RequestMethod.PUT })
	public void update(@PathVariable("id") String id, @RequestParam("operation") String operation,
			@RequestParam("idType") String idType, @RequestBody Thing data) throws ThingabledRestException {

		Thing entity = null;
		if (idType.equals("thingId")) {
			entity = thingRepository.findByIdAndStatus(id, Status.ENABLED);

		}
		if (idType.equals("publicKey")) {
			entity = thingRepository.findByPublickeyAndStatus(id, Status.ENABLED);
		}

		if (entity == null) {
			throw new ThingabledRestException(ErrorCode.THING_NOT_FOUND);
		}

		if (operation.equals("own")) {
			own(entity);
		}
	}

	private void own(Thing entity) throws ThingabledRestException {
		ThingabledUserDetails userDetails = (ThingabledUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		// get owner
		ThingOwnership ownership_ = thingOwnershipRepository.findByThingIdAndStatus(entity.getId(), Status.ENABLED);
		if (ownership_ != null) {
			throw new ThingabledRestException(ErrorCode.THING_ALREADY_OWNED);
		}
		ThingOwnership ownership = ThingOwnership.newEntity();
		ownership.setThingId(entity.getId());
		ownership.setUserId(userDetails.getId());
		thingOwnershipRepository.save(ownership);

	}
	
	private void disown(HttpServletResponse response, String id, String idType, String operation)
			throws ThingabledRestException {

		Thing entity = null;
		if (idType.equals("thingId")) {
			entity = thingRepository.findByIdAndStatus(id, Status.ENABLED);

		}
		if (idType.equals("publicKey")) {
			entity = thingRepository.findByPublickeyAndStatus(id, Status.ENABLED);
		}
		if (entity == null) {
			throw new ThingabledRestException(ErrorCode.THING_NOT_FOUND);
		}

		ThingabledUserDetails userDetails = (ThingabledUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		ThingContext context = thingContextRepository.findByIdAndStatus(entity.getThingContextId(), Status.ENABLED);
		if (context == null) {
			throw new ThingabledRestException(ErrorCode.THING_CONTEXT_NOT_FOUND);
		}
		if (context.getType() == Type.VIRTUAL) {
			throw new ThingabledRestException(ErrorCode.CAN_NOT_DISOWN_VIRTUAL_THINGS);
		}
		ThingOwnership ownership = thingOwnershipRepository.findByThingIdAndUserIdAndStatus(
				entity.getId(), userDetails.getId(),  Status.ENABLED);
		if (ownership==null) {
			throw new ThingabledRestException(ErrorCode.THING_NOT_OWNED);
		}
		
		ownership.disable();
		thingOwnershipRepository.save(ownership);
		
		
	}

	/*
	 * @RequestMapping(value = { "/share" }, method = { RequestMethod.PUT })
	 * public void share( @PathVariable("thingId") String thingId,
	 * 
	 * @PathVariable("thingPointId") String thingPointId, @RequestBody
	 * ThingPermission data) throws ThingException {
	 * 
	 * ThingabledUserDetails userDetails = (ThingabledUserDetails)
	 * SecurityContextHolder.getContext() .getAuthentication().getPrincipal();
	 * 
	 * Thing entity = repository.findByIdAndStatus(thingId, Status.ENABLED); if
	 * (entity == null) { throw new ThingNotFoundException(); }
	 * List<ThingOwnership> ownerships = thingOwnershipRepository
	 * .findByThingIdAndThingPointIdAndUserIdAndOwnershipAndStatus(thingId,
	 * thingPointId, userDetails.getId(), Ownership.OWNER, Status.ENABLED); if
	 * (ownerships.size() == 0) { throw new ThingNotOwnedException(); }
	 * 
	 * ThingPermission thingPermission =
	 * thingPermissionRepository.findByThingIdAndThingPointIdAndStatus(thingId,
	 * thingPointId, Status.ENABLED);
	 * 
	 * if (thingPermission == null) { throw new NoThingPermissionException(); }
	 * 
	 * thingPermission.setPermission(data.getPermission());
	 * thingPermission.setThingId(data.getThingId());
	 * thingPermission.setThingPointId(data.getThingPointId());
	 * thingPermission.setUserIdSharedWith(data.getUserIdSharedWith());
	 * thingPermissionRepository.save(thingPermission);
	 * 
	 * }
	 */

}
