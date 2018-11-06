package com.goldennode.server.controllers.rest.controllers;

import java.security.Principal;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.goldennode.commons.entity.Thing;
import com.goldennode.commons.entity.ThingData;
import com.goldennode.commons.entity.ThingPoint;
import com.goldennode.commons.entity.BaseEntity.Status;
import com.goldennode.commons.repository.ThingPointRepository;
import com.goldennode.commons.repository.ThingRepository;
import com.goldennode.commons.util.DateTimeUtils;
import com.goldennode.server.controllers.rest.ErrorCode;
import com.goldennode.server.controllers.rest.GoldenNodeRestException;

@RestController
@RequestMapping(value = { "/restnoauth/thingdata" })
@CrossOrigin(origins = "*")
public class ThingDataControllerNoAuth {

	private static final Logger LOGGER = LoggerFactory.getLogger(ThingDataControllerNoAuth.class);

	@Autowired
	private ThingPointRepository thingPointRepository;

	@Autowired
	private ThingRepository thingRepository;
	@Autowired
	private ServletContext context;

	@RequestMapping(method = { RequestMethod.POST })
	public void add(Principal principal, HttpServletRequest request, HttpServletResponse response,
			@RequestParam("publickey") String publickey, @RequestParam("thingPointId") String thingPointId,
			@RequestParam("value") String value) throws GoldenNodeRestException {

		ThingPoint thingPointEntity = thingPointRepository.findByIdAndStatus(thingPointId, Status.ENABLED);
		if (thingPointEntity == null) {
			throw new GoldenNodeRestException(ErrorCode.THING_POINT_NOT_FOUND);
		}

		Thing entity = thingRepository.findByPublickeyAndStatus(publickey, Status.ENABLED);
		if (entity == null) {
			throw new GoldenNodeRestException(ErrorCode.THING_NOT_FOUND);
		}

		ThingData newEntity = new ThingData();
		newEntity.setThingPointId(thingPointId);
		newEntity.setTime(DateTimeUtils.getGmtDate());
		newEntity.setValue(value);// TODO constrain value according to
		
		if (entity.getLatitude() != null && entity.getLongitude() != null) {
			newEntity.setLatitude(entity.getLatitude());
			newEntity.setLongitude(entity.getLongitude());
		}
		


		ThingDataController.writeToInflux(newEntity,context);

	}

	
}
