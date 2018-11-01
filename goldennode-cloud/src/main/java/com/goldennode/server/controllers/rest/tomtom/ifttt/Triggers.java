package com.goldennode.server.controllers.rest.tomtom.ifttt;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thingabled.commons.entity.IftttTrigger;
import com.thingabled.commons.entity.Users;
import com.thingabled.commons.repository.IftttTriggerRepository;
import com.thingabled.commons.repository.UserRepository;

@RestController
@CrossOrigin(origins = "*")
public class Triggers {

	@Autowired
	private IftttTriggerRepository iftttTriggerRepository;
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = {
			"/tomtom/ifttt/v1/triggers/reached_destination" }, method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResponseEntity<String> reached_destination(@RequestBody String request) throws IFTTTRestException {
		return new ResponseEntity<String>(getTriggers("reached_destination", request).toString(), HttpStatus.OK);
	}

	@RequestMapping(value = {
			"/tomtom/ifttt/v1/triggers/hourly_location_information" }, method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResponseEntity<String> hourly_location_information(@RequestBody String request) throws IFTTTRestException {
		return new ResponseEntity<String>(getTriggers("hourly_location_information", request).toString(),
				HttpStatus.OK);
	}

	@RequestMapping(value = {
			"/tomtom/ifttt/v1/triggers/reached_home" }, method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResponseEntity<String> reached_home(@RequestBody String request) throws IFTTTRestException {
		return new ResponseEntity<String>(getTriggers("reached_home", request).toString(), HttpStatus.OK);
	}

	@RequestMapping(value = {
			"/tomtom/ifttt/v1/triggers/reached_work" }, method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResponseEntity<String> reached_work(@RequestBody String request) throws IFTTTRestException {
		return new ResponseEntity<String>(getTriggers("reached_work", request).toString(), HttpStatus.OK);
	}

	@RequestMapping(value = {
			"/tomtom/ifttt/v1/triggers/reached_school" }, method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResponseEntity<String> reached_school(@RequestBody String request) throws IFTTTRestException {
		return new ResponseEntity<String>(getTriggers("reached_school", request).toString(), HttpStatus.OK);
	}

	@RequestMapping(value = {
			"/tomtom/ifttt/v1/triggers/new_gym_activity" }, method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResponseEntity<String> new_gym_activity(@RequestBody String request) throws IFTTTRestException {
		return new ResponseEntity<String>(getTriggers("new_gym_activity", request).toString(), HttpStatus.OK);
	}

	@RequestMapping(value = {
			"/tomtom/ifttt/v1/triggers/calorie_goal_reached" }, method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResponseEntity<String> calorie_goal_reached(@RequestBody String request) throws IFTTTRestException {
		return new ResponseEntity<String>(getTriggers("calorie_goal_reached", request).toString(), HttpStatus.OK);
	}

	@RequestMapping(value = {
			"/tomtom/ifttt/v1/triggers/new_tomtombandit_story" }, method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResponseEntity<String> new_tomtombandit_story(@RequestBody String request) throws IFTTTRestException {
		return new ResponseEntity<String>(getTriggers("new_tomtombandit_story", request).toString(), HttpStatus.OK);
	}

	@RequestMapping(value = {
			"/tomtom/ifttt/v1/triggers/heavy_traffic_in_your_area" }, method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResponseEntity<String> heavy_traffic_in_your_area(@RequestBody String request) throws IFTTTRestException {
		return new ResponseEntity<String>(getTriggers("heavy_traffic_in_your_area", request).toString(), HttpStatus.OK);
	}

	@RequestMapping(value = {
			"/tomtom/ifttt/v1/triggers/light_traffic_in_your_area" }, method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResponseEntity<String> light_traffic_in_your_area(@RequestBody String request) throws IFTTTRestException {
		return new ResponseEntity<String>(getTriggers("light_traffic_in_your_area", request).toString(), HttpStatus.OK);
	}

	@RequestMapping(value = {
			"/tomtom/ifttt/v1/triggers/route_planned" }, method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResponseEntity<String> route_planned(@RequestBody String request) throws IFTTTRestException {
		return new ResponseEntity<String>(getTriggers("route_planned", request).toString(), HttpStatus.OK);
	}

	private JSONObject getTriggers(String name, String request) throws IFTTTRestException {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users usr = userRepository.findByEmail(user.getUsername());
		if (usr == null) {
			throw new IFTTTRestException("User not found");
		}
		List<IftttTrigger> triggers = iftttTriggerRepository.findTop50ByNameAndUseridOrderByTimestampDesc(name,
				usr.getId());

		JSONObject obj = new JSONObject(request);
		int limit = 50;
		if (obj.has("limit")) {
			limit = obj.getInt("limit");
		}
		JSONObject json = new JSONObject();
		JSONArray jtriggers = new JSONArray();
		for (int x = 0; x < (triggers.size() < limit ? triggers.size() : limit); x++) {
			IftttTrigger trigger = triggers.get(x);

			JSONObject jtrigger = new JSONObject();

			JSONObject jmeta = new JSONObject();
			jmeta.put("id", trigger.getId());
			jmeta.put("timestamp", trigger.getTimestamp());
			jtrigger.put("meta", jmeta);
			jtrigger.put("created_at", getISO8601DateTime(trigger.getTimestamp()));
			JSONObject data = new JSONObject(trigger.getData());
			Iterator<String> iter = data.keys();
			while (iter.hasNext()) {
				String key = iter.next();
				Object value = data.get(key);
				jtrigger.put(key, value);
			}
			jtriggers.put(jtrigger);
		}
		json.put("data", jtriggers);
		return json;
	}

	public static String getISO8601DateTime(long unixTimeStamp) {
		TimeZone tz = TimeZone.getTimeZone("GMT");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
		df.setTimeZone(tz);
		Date d = new Date(unixTimeStamp * 1000);
		String nowAsISO = df.format(d);
		return nowAsISO;
	}

}
