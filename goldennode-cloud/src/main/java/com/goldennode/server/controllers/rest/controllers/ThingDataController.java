package com.goldennode.server.controllers.rest.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.goldennode.commons.entity.ThingData;
import com.goldennode.commons.entity.ThingOwnership;
import com.goldennode.commons.entity.ThingPoint;
import com.goldennode.commons.entity.BaseEntity.Status;
import com.goldennode.commons.repository.ThingOwnershipRepository;
import com.goldennode.commons.repository.ThingPointRepository;
import com.goldennode.commons.util.DateTimeUtils;
import com.goldennode.commons.util.Interval;
import com.goldennode.commons.util.URLUtils;
import com.goldennode.commons.util.DateTimeUtils.Period;
import com.goldennode.server.controllers.rest.ErrorCode;
import com.goldennode.server.controllers.rest.GoldenNodeRestException;
import com.goldennode.server.security.GoldenNodeUserDetails;

@RestController
@RequestMapping(value = { "/rest/thingdata","/rest/pt/thingdata" })
@CrossOrigin(origins = "*")
public class ThingDataController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ThingDataController.class);

	@Autowired
	private ThingOwnershipRepository thingOwnershipRepository;
	@Autowired
	private ThingPointRepository thingPointRepository;

	@Autowired
	private ServletContext context;

	@RequestMapping(method = { RequestMethod.POST })
	public void add(Principal principal, HttpServletRequest request, HttpServletResponse response,
			@RequestBody ThingData data) throws GoldenNodeRestException {
		GoldenNodeUserDetails userDetails = (GoldenNodeUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		ThingPoint thingPointEntity = thingPointRepository.findByIdAndStatus(data.getThingPointId(), Status.ENABLED);
		if (thingPointEntity == null) {
			throw new GoldenNodeRestException(ErrorCode.THING_POINT_NOT_FOUND);
		}

		ThingOwnership ownership = thingOwnershipRepository
				.findByThingIdAndUserIdAndStatus(thingPointEntity.getThingId(), userDetails.getId(), Status.ENABLED);
		if (ownership == null) {
			throw new GoldenNodeRestException(ErrorCode.THING_NOT_OWNED);
		}

		ThingData newEntity = new ThingData();
		newEntity.setThingPointId(data.getThingPointId());
		newEntity.setTime(DateTimeUtils.getGmtDate());
		newEntity.setValue(data.getValue());// TODO constrain value according to
											// unit
		if (data.getLatitude() == null || data.getLongitude() == null) {
			if (thingPointEntity.getThing().getLatitude() != null
					&& thingPointEntity.getThing().getLongitude() != null) {
				newEntity.setLatitude(thingPointEntity.getThing().getLatitude());
				newEntity.setLongitude(thingPointEntity.getThing().getLongitude());
			}
		} else {
			newEntity.setLatitude(data.getLatitude());
			newEntity.setLongitude(data.getLongitude());

		}
		writeToInflux(newEntity, context);
	}

	public static void writeToInflux(ThingData newEntity, ServletContext context) {

		String plainCreds = context.getInitParameter("influx.user") + ":" + context.getInitParameter("influx.password");
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.getEncoder().encode(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		if (newEntity.getLatitude() == null || newEntity.getLongitude() == null) {
			newEntity.setLatitude((double) Integer.MAX_VALUE);
			newEntity.setLongitude((double) Integer.MAX_VALUE);
		}
		HttpEntity<String> request = new HttpEntity<String>("thingdata,thingPointId=" + newEntity.getThingPointId()
				+ " value=" + newEntity.getValue() + ",latitude=" + newEntity.getLatitude() + ",longitude="
				+ newEntity.getLongitude() /*
											 * + " " + DateTimeUtils.
											 * getGmtUnixTimestamp()
											 */, headers);
		// TODO set timestamp
		RestTemplate restTemplate = new RestTemplate();
		String status = null;
		String body = null;
		ResponseEntity<String> response = null;

		response = restTemplate.exchange(context.getInitParameter("influx.url") + "/write?time_precision=s&db="
				+ context.getInitParameter("influx.db"), HttpMethod.POST, request, String.class);
		status = response.getStatusCode().toString();
		body = response.getBody();

	}

	@RequestMapping(value = { "/{thingPointId}" }, method = { RequestMethod.GET })
	public Set<Interval> get(Principal principal, @PathVariable("thingPointId") String thingPointId,
			@RequestParam("period") String period, @RequestParam("type") String type) throws GoldenNodeRestException {
		try {
			GoldenNodeUserDetails userDetails = (GoldenNodeUserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();

			ThingPoint thingPoint = thingPointRepository.findByIdAndStatus(thingPointId, Status.ENABLED);
			if (thingPoint == null) {
				throw new GoldenNodeRestException(ErrorCode.THING_POINT_NOT_FOUND);
			}
			// get owner
			ThingOwnership ownership = thingOwnershipRepository.findByThingIdAndUserIdAndStatus(thingPoint.getThingId(),
					userDetails.getId(), Status.ENABLED);
			if (ownership == null) {
				throw new GoldenNodeRestException(ErrorCode.THING_NOT_OWNED);
			}

			TimeZone appTimeZone = TimeZone.getTimeZone("GMT");
			TreeSet<Interval> intervals = DateTimeUtils.getIntervals(Period.valueOf(period),
					com.goldennode.commons.util.DateTimeUtils.Type.valueOf(type), appTimeZone);

			Interval intervalFromPeriod = DateTimeUtils.getIntervalFromPeriod(Period.valueOf(period), appTimeZone);

			intervalFromPeriod.setStartDate(DateTimeUtils.getGmtDate(intervalFromPeriod.getStartDate(), appTimeZone));

			intervalFromPeriod.setEndDate(DateTimeUtils.getGmtDate(intervalFromPeriod.getEndDate(), appTimeZone));

			JSONArray array = readFromInflux(thingPointId, intervalFromPeriod.getStartDate(),
					intervalFromPeriod.getEndDate(), context);

			if (array != null) {
				Iterator iter = array.iterator();
				while (iter.hasNext()) {

					JSONArray arr2 = (JSONArray) iter.next();

					ThingData newData = new ThingData();
					if (arr2.get(2) != null)
						newData.setLatitude(arr2.getDouble(2));
					if (arr2.get(3) != null)
						newData.setLongitude(arr2.getDouble(3));
					newData.setThingPointId(thingPointId);
					newData.setTime(DateTimeUtils.getLocalDate(
							new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(arr2.get(0).toString()), appTimeZone));
					newData.setValue(arr2.get(1).toString());

					Calendar cal = Calendar.getInstance();
					cal.setTime(DateTimeUtils.getLocalDate(newData.getTime(), appTimeZone));
					Interval interval = intervals.floor(new Interval(cal.getTime(), cal.getTime(), null));
					interval.addData(newData);
				}

			}
			return intervals;
		} catch (ParseException | IOException e) {
			LOGGER.error("error",e);
			throw new GoldenNodeRestException(ErrorCode.GENERAL_ERROR);
		}

	}

	public static JSONArray readFromInflux(String thingPointId, Date start, Date end, ServletContext context) throws IOException {

		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String query = null;

		if (start == null || end == null) {
			query = "SELECT time,value,latitude,longitude,thingPointId FROM thingdata where thingPointId='"
					+ thingPointId + "' order by time desc limit 1";
		} else {
			query = "SELECT time,value,latitude,longitude,thingPointId FROM thingdata where thingPointId='"
					+ thingPointId + "' and time >= '" + dt.format(start) + "' and time < '" + dt.format(end) + "'";
		}

		LOGGER.debug("query=" + query);

		String url = context.getInitParameter("influx.url") + "/query?db=" + context.getInitParameter("influx.db")
				+ "&q=" + URLUtils.encode(query);

		LOGGER.debug("url=" + url);
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");

		String plainCreds = "avgbreathe" + ":" + "avgbreathe2016";
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.getEncoder().encode(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);

		con.addRequestProperty("Authorization", "Basic " + base64Creds);

		int responseCode = con.getResponseCode();
		LOGGER.debug("Sending 'GET' request to URL : " + url);
		LOGGER.debug("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}

		in.close();

		// print result
		LOGGER.debug(response.toString());

		JSONObject json = new JSONObject(response.toString());
		JSONObject result = ((JSONArray) json.get("results")).getJSONObject(0);
		LOGGER.debug("Result:" + result.toString());
		if (result.has("series")) {
			JSONObject serie = ((JSONArray) result.get("series")).getJSONObject(0);
			LOGGER.debug("Serie:" + serie.toString());

			JSONArray values = ((JSONArray) serie.get("values"));
			LOGGER.debug("Values:" + values.toString());

			return values;
		}
		return null;

	}

}
