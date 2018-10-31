package com.thingabled.server.controllers.rest.tomtom.ifttt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thingabled.commons.entity.IftttAction;
import com.thingabled.commons.entity.Users;
import com.thingabled.commons.repository.IftttActionRepository;
import com.thingabled.commons.repository.UserRepository;

@RestController
@CrossOrigin(origins = "*")
public class GetAction {

	@Autowired
	private IftttActionRepository iftttActionRepository;
	@Autowired
	private UserRepository userRepository;

	@Transactional(isolation = Isolation.SERIALIZABLE)
	@RequestMapping(value = { "/rest/iftttactions/{actionName}" }, method = { RequestMethod.GET })
	public List<IftttAction> getAction(@PathVariable("actionName") String actionName) throws IFTTTRestException {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users usr = userRepository.findByEmail(user.getUsername());
		if (usr == null) {
			throw new IFTTTRestException("User not found");
		}
		List<IftttAction> actions = null;
		actions = iftttActionRepository.findByNameAndUseridAndProcessed(actionName, usr.getId(), 0);
		for (IftttAction action : actions) {
			action.setProcessed(1);
			iftttActionRepository.save(action);
		}
		return actions;
	}

}
