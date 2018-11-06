package com.goldennode.server.controllers.rest.tomtom.ifttt;

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
import com.goldennode.commons.entity.IftttAction;
import com.goldennode.commons.entity.Users;
import com.goldennode.commons.repository.IftttActionRepository;
import com.goldennode.commons.repository.UserRepository;

@RestController
@CrossOrigin(origins = "*")
public class Actions {

	@Autowired
	private IftttActionRepository iftttActionRepository;
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = {
			"/tomtom/ifttt/v1/actions/navigate_destination" }, method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResponseEntity<String> navigate_destination(@RequestBody String body) throws IFTTTRestException {
		return new ResponseEntity<String>(saveAction("navigate_destination", body, new String[] { "address" }).toString(),
				HttpStatus.OK);
	}

	@RequestMapping(value = {
			"/tomtom/ifttt/v1/actions/navigate_home" }, method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResponseEntity<String> navigate_home(@RequestBody String body) throws IFTTTRestException {
		return new ResponseEntity<String>(saveAction("navigate_home", body, new String[] {}).toString(), HttpStatus.OK);
	}

	@RequestMapping(value = {
			"/tomtom/ifttt/v1/actions/navigate_work" }, method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResponseEntity<String> navigate_work(@RequestBody String body) throws IFTTTRestException {
		return new ResponseEntity<String>(saveAction("navigate_work", body, new String[] {}).toString(), HttpStatus.OK);
	}

	@RequestMapping(value = {
			"/tomtom/ifttt/v1/actions/navigate_school" }, method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResponseEntity<String> navigate_school(@RequestBody String body) throws IFTTTRestException {
		return new ResponseEntity<String>(saveAction("navigate_school", body, new String[] {}).toString(), HttpStatus.OK);
	}

	@RequestMapping(value = {
			"/tomtom/ifttt/v1/actions/update_my_weight" }, method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResponseEntity<String> update_my_weight(@RequestBody String body) throws IFTTTRestException {
		return new ResponseEntity<String>(saveAction("update_my_weight", body, new String[] { "weight" }).toString(),
				HttpStatus.OK);
	}

	@RequestMapping(value = {
			"/tomtom/ifttt/v1/actions/update_my_height" }, method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResponseEntity<String> update_my_height(@RequestBody String body) throws IFTTTRestException {
		return new ResponseEntity<String>(saveAction("update_my_height", body, new String[] { "height" }).toString(),
				HttpStatus.OK);
	}

	private JSONObject saveAction(String name, String body, String[] actionFields) throws IFTTTRestException {
		JSONObject obj = new JSONObject(body);
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users usr = userRepository.findByEmail(user.getUsername());
		if (usr == null) {
			throw new IFTTTRestException("User not found");
		}
		IftttAction action = IftttAction.newEntity();
		if (obj.has("ifttt_source")) {
			action.setIftttSourceId(obj.getJSONObject("ifttt_source").getString("id"));
			action.setIftttSourceUrl(obj.getJSONObject("ifttt_source").getString("url"));
		}
		action.setName(name);
		action.setUserid(usr.getId());
		action.setUserTimezone(obj.getJSONObject("user").getString("timezone"));

		for (String field : actionFields) {
			if (!obj.has("actionFields") || !obj.getJSONObject("actionFields").has(field)) {
				throw new IFTTTRestException("Missing 'actionFields'");
			}
		}
		if (obj.has("actionFields"))
			action.setData(obj.getJSONObject("actionFields").toString(1));
		else
			action.setData("{}");
		iftttActionRepository.save(action);
		JSONObject json = new JSONObject();
		JSONArray jdata = new JSONArray();
		JSONObject act = new JSONObject();
		act.put("id", action.getId());
		jdata.put(act);
		json.put("data", jdata);
		return json;
	}

}
