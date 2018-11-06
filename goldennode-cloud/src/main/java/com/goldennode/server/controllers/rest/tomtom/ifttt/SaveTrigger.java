package com.goldennode.server.controllers.rest.tomtom.ifttt;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.goldennode.commons.entity.IftttTrigger;
import com.goldennode.commons.entity.Users;
import com.goldennode.commons.repository.IftttTriggerRepository;
import com.goldennode.commons.repository.UserRepository;
import com.goldennode.commons.util.UUID;

@RestController
@CrossOrigin(origins = "*")
public class SaveTrigger {

	@Autowired
	private IftttTriggerRepository iftttTriggerRepository;
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/rest/ifttttriggers", method = RequestMethod.POST)
	public ResponseEntity<Object> saveTrigger(@RequestBody String requestBody, HttpServletRequest request)
			throws IFTTTRestException {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users usr = userRepository.findByEmail(user.getUsername());
		if (usr == null) {
			throw new IFTTTRestException("User not found");
		}
		
		JSONObject body=new JSONObject(requestBody);
		
		IftttTrigger trigger = IftttTrigger.newEntity();
		if (body.has("data"))
			trigger.setData(body.getJSONObject("data").toString());
		else{
			trigger.setData("{}");
		}
		trigger.setName(body.getString("name"));
		trigger.setUserid(usr.getId());
		iftttTriggerRepository.save(trigger);
		HttpHeaders headers = new HttpHeaders();
		headers.add("IFTTT-Channel-Key", request.getServletContext().getInitParameter("iftttKey"));
		headers.add("X-Request-ID", UUID.getUUID());
		headers.add("Accept", "application/json");
		headers.add("Accept-Charset", "utf-8");
		headers.add("Accept-Encoding", "gzip, deflate");
		headers.add("Content-Type", "application/json");
		ResponseEntity<String> response = new RestTemplate().exchange("https://realtime.ifttt.com/v1/notifications",
				HttpMethod.POST,
				new HttpEntity<String>("{\"data\": [{\"user_id\": \"" + user.getUsername() + "\"}]}", headers),
				String.class);
		return new ResponseEntity<Object>(response.getBody(), response.getStatusCode());

	}

}
