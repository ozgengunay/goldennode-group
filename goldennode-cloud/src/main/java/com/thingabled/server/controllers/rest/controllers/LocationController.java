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

import com.thingabled.commons.entity.BaseEntity.Status;
import com.thingabled.commons.entity.Location;
import com.thingabled.commons.repository.LocationRepository;
import com.thingabled.server.controllers.rest.ErrorCode;
import com.thingabled.server.controllers.rest.ThingabledRestException;
import com.thingabled.server.security.ThingabledUserDetails;

@RestController
@RequestMapping(value = { "/rest/locations" })
@CrossOrigin(origins="*")
public class LocationController {
	@Autowired
	private LocationRepository repository;

	@RequestMapping(value = { "/{locationId}" }, method = { RequestMethod.GET })
	public Location get(Principal principal, @PathVariable("locationId") String locationId)
			throws ThingabledRestException {
		ThingabledUserDetails userDetails = (ThingabledUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Location entity = repository.findByIdAndUserIdAndStatus(locationId, userDetails.getId(), Status.ENABLED);
		if (entity == null) {
			throw new ThingabledRestException(ErrorCode.LOCATION_NOT_FOUND);
		}
		return entity;
	}
	

	@RequestMapping(method = { RequestMethod.GET })
	public List<Location> getAll(Principal principal) {
		ThingabledUserDetails userDetails = (ThingabledUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		return repository.findByUserIdAndStatus(userDetails.getId(), Status.ENABLED);
	}

	@RequestMapping(method = { RequestMethod.POST })
	public void add(Principal principal, HttpServletRequest request, HttpServletResponse response,
			@RequestBody Location data) {
		ThingabledUserDetails userDetails = (ThingabledUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Location newEntity = Location.newEntity();
		newEntity.setLatitude(data.getLatitude());
		newEntity.setLongitude(data.getLongitude());
		newEntity.setName(data.getName());
		newEntity.setUserId(userDetails.getId());
		newEntity = repository.save(newEntity);
		response.setHeader("Location", request.getRequestURI() + newEntity.getId());
	}

	@RequestMapping(value = { "/{locationId}" }, method = { RequestMethod.PUT })
	public Location update(Principal principal, @PathVariable("locationId") String locationId,
			@RequestBody Location data) throws ThingabledRestException {
		ThingabledUserDetails userDetails = (ThingabledUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Location entity = repository.findByIdAndUserIdAndStatus(locationId, userDetails.getId(), Status.ENABLED);
		if (entity == null) {
			throw new ThingabledRestException(ErrorCode.LOCATION_NOT_FOUND);
		}
		entity.setLatitude(data.getLatitude());
		entity.setLongitude(data.getLongitude());
		entity.setName(data.getName());

		return repository.save(entity);

	}

	@RequestMapping(value = { "/{locationId}" }, method = { RequestMethod.DELETE })
	public void delete(Principal principal, @PathVariable("locationId") String locationId)
			throws ThingabledRestException {
		ThingabledUserDetails userDetails = (ThingabledUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Location entity = repository.findByIdAndUserIdAndStatus(locationId, userDetails.getId(), Status.ENABLED);
		if (entity == null) {
			throw new ThingabledRestException(ErrorCode.LOCATION_NOT_FOUND);
		}
		entity.disable();

		repository.save(entity);
	}

}
