package com.goldennode.server.controllers.rest.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FamilyMember;
import org.springframework.social.facebook.api.FriendOperations;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thingabled.commons.entity.Friendship;
import com.thingabled.commons.entity.Users;
import com.thingabled.commons.repository.FriendshipRepository;
import com.thingabled.commons.repository.UserRepository;
import com.goldennode.server.controllers.rest.ErrorCode;
import com.goldennode.server.controllers.rest.ThingabledRestException;
import com.goldennode.server.security.ThingabledUserDetails;

@RestController
@RequestMapping(value = { "/rest/friendships" })
@CrossOrigin(origins = "*")
public class FriendshipController {
	@Autowired
	private FriendshipRepository repository;

	@Autowired
	private UsersConnectionRepository userConnectionRepository;
	
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = { "/{friendshipId}" }, method = { RequestMethod.GET })
	public Friendship get(Principal principal, @PathVariable("friendshipId") String friendshipId)
			throws ThingabledRestException {
		ThingabledUserDetails userDetails = (ThingabledUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Friendship entity = repository.findByIdAndUserId(friendshipId, userDetails.getId());
		if (entity == null) {
			throw new ThingabledRestException(ErrorCode.ENTITY_NOT_FOUND);
		}
		return entity;
	}


	@RequestMapping(method = { RequestMethod.GET })
	public List<Friendship> getAll(Principal principal, HttpServletRequest request) {
		ThingabledUserDetails userDetails = (ThingabledUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		
		List<Friendship> friendships = repository.findByUserId(userDetails.getId());
		for (Friendship friendship : friendships) {
			repository.delete(friendship);
		}
		String accessToken = request.getParameter("input_token");
		Facebook facebook = new FacebookTemplate(accessToken);
		FriendOperations operations = facebook.friendOperations();
		
		List<String> ids = operations.getFriendIds();
		List <FamilyMember> fms= operations.getFamily();
		List<FL> lists=new ArrayList<FL>();
		for (FamilyMember fm : fms) {
			FL fl=new FL(); 
			fl.setId(fm.getId());
			fl.setListName("#FAMILY#"+fm.getRelationship());
			lists.add(fl);
		}
		//facebook.restOperations().
		
		
		for (String id : ids) {
			Set<String> set = new HashSet<String>();
			set.add(id);
			Set<String> uids = userConnectionRepository.findUserIdsConnectedTo("facebook", set);
			for (String uid : uids) {
				Friendship f = Friendship.newEntity();
				f.setUserId(userDetails.getId());
				Users user=userRepository.findByEmail(uid);
				if(user!=null){
					f.setUserIdFriend(userRepository.findByEmail(uid).getId());
					repository.save(f);
				}
			}
		}
		return repository.findByUserId(userDetails.getId());
	}
	
	
	class FL{
		private String id;
		private String listName;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getListName() {
			return listName;
		}
		public void setListName(String listName) {
			this.listName = listName;
		}
		
		
	}


}
