package com.goldennode.server.controllers.rest.controllers;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.ServletContext;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.goldennode.commons.entity.Friendship;
import com.goldennode.commons.entity.Thing;
import com.goldennode.commons.entity.ThingContext;
import com.goldennode.commons.entity.ThingData;
import com.goldennode.commons.entity.ThingOwnership;
import com.goldennode.commons.entity.ThingPoint;
import com.goldennode.commons.entity.Users;
import com.goldennode.commons.entity.BaseEntity.Status;
import com.goldennode.commons.entity.ThingContext.Type;
import com.goldennode.commons.entity.ThingPoint.Permission;
import com.goldennode.commons.repository.FriendshipRepository;
import com.goldennode.commons.repository.ThingContextRepository;
import com.goldennode.commons.repository.ThingOwnershipRepository;
import com.goldennode.commons.repository.ThingPointRepository;
import com.goldennode.commons.repository.ThingRepository;
import com.goldennode.commons.repository.UserRepository;
import com.goldennode.commons.util.DateTimeUtils;
import com.goldennode.server.controllers.rest.ErrorCode;
import com.goldennode.server.controllers.rest.GoldenNodeRestException;
import com.goldennode.server.controllers.rest.json.SocialUserThingDataSnapshot;
import com.goldennode.server.security.GoldenNodeUserDetails;

@RestController
@RequestMapping(value = { "/rest/socialoperations" })
@CrossOrigin(origins = "*")
public class SocialOperations {

	private static final Logger LOGGER = LoggerFactory.getLogger(SocialOperations.class);
	@Autowired
	private ThingRepository thingRepository;

	@Autowired
	private ThingOwnershipRepository thingOwnershipRepository;
	@Autowired
	private ThingPointRepository thingPointRepository;
	
	@Autowired
	private ThingContextRepository thingContextRepository;
	@Autowired
	private FriendshipRepository friendshipRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ServletContext context;
	
	@Autowired
	private UsersConnectionRepository userConnectionRepository;

	
	//TODO implement friendList variable
	@RequestMapping(value = { "/getFriendList/{friendList}" }, method = {
			RequestMethod.GET },produces = "application/json")
	public String getFriendList( @PathVariable("friendList") String friendList)
					throws GoldenNodeRestException{
		try {
			GoldenNodeUserDetails userDetails = (GoldenNodeUserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();

			List<SocialUserThingDataSnapshot> snapshots = new ArrayList<SocialUserThingDataSnapshot>();
			Set<String> friendUserIds = getFriends(userDetails.getId());
			JSONArray friends=new JSONArray(); 
			for (String userId : friendUserIds) {
				Users user=userRepository.findById(userId);
				ConnectionRepository repo = userConnectionRepository.createConnectionRepository(user.getEmail());
				List<Connection<?>> cons= repo.findConnections("facebook");
				for (Connection<?> con : cons) {
					JSONObject jo=new JSONObject();
					jo.put("id",userId );
					jo.put("providerUserId",con.getKey().getProviderUserId() );
					jo.put("displayName",con.getDisplayName() );
					jo.put("imageUrl",con.getImageUrl() );
					friends.put(jo);
				}
				
			}
		
			JSONObject joFinal=new JSONObject();
			joFinal.put("friends", friends);
			return joFinal.toString();
		} catch (Exception e) {
			LOGGER.error("error in getFriendsThingDataSnapshot", e);
			throw new GoldenNodeRestException(ErrorCode.GENERAL_ERROR);
		}

	}

	
	
	
	
	// TODO remove internalId
	@RequestMapping(value = { "/getFriendsThingPointData/{thingContextId}/{internalId}" }, method = {
			RequestMethod.GET })
	public List<SocialUserThingDataSnapshot> getFriendsThingDataSnapshot(Principal principal,
			@PathVariable("thingContextId") String thingContextId, @PathVariable("internalId") String internalId)
					throws GoldenNodeRestException{
		try {
			GoldenNodeUserDetails userDetails = (GoldenNodeUserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();

			ThingContext thingContext = thingContextRepository.findByIdAndStatus(thingContextId, Status.ENABLED);
			if (thingContext == null) {
				throw new GoldenNodeRestException(ErrorCode.THING_CONTEXT_NOT_FOUND);
			}

			if (thingContext.getType() != Type.VIRTUAL) {
				throw new GoldenNodeRestException(ErrorCode.THING_CONTEXT_NOT_FOUND);
			}

			List<SocialUserThingDataSnapshot> snapshots = new ArrayList<SocialUserThingDataSnapshot>();
			Set<String> friends = getFriends(userDetails.getId());
			List<Thing> things = thingRepository.findByThingContextIdAndStatus(thingContextId, Status.ENABLED);
			for (Thing thing : things) {
				ThingOwnership ownership = thingOwnershipRepository.findByThingIdAndStatus(thing.getId(),
						Status.ENABLED);
				if (ownership != null) {
					if (friends.contains(ownership.getUserId())) {
						String thingId = ownership.getThingId();
						List<ThingPoint> thingPoints = thingPointRepository
								.findByThingIdAndStatusOrderByCreationTimeDesc(thingId, Status.ENABLED);
						for (ThingPoint thingPoint : thingPoints) {
							if (thingPoint.getInternalId()!=null && thingPoint.getInternalId().contains(internalId)
									&& (thingPoint.getPermission() == Permission.FRIENDS
											|| thingPoint.getPermission() == Permission.PUBLIC)) {

								JSONArray array = ThingDataController.readFromInflux(thingPoint.getId(), null, null,
										context);

								if (array != null) {
									Iterator iter = array.iterator();
									while (iter.hasNext()) {
										JSONArray arr = (JSONArray) iter.next();
										Users user = userRepository.findById(ownership.getUserId());
										if (user != null) {
											SocialUserThingDataSnapshot snapshot = new SocialUserThingDataSnapshot();
											snapshot.setName(user.getFirstName() + " " + user.getLastName());
											snapshot.setUserId(user.getId());
											snapshot.setEmail(user.getEmail());

											ThingData td = new ThingData();
											td.setThingPointId(thingPoint.getId());
											td.setLatitude(arr.getDouble(2));
											td.setLongitude(arr.getDouble(3));
											td.setTime(
													DateTimeUtils.getLocalDate(
															new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
																	.parse(arr.get(0).toString()),
													TimeZone.getTimeZone("GMT")));
											td.setValue(arr.get(1).toString());
											snapshot.setThingData(td);
											snapshots.add(snapshot);
										}
									}
								}

								break;
							}
						}

					}
				}

			}
			return snapshots;
		} catch (Exception e) {
			LOGGER.error("error in getFriendsThingDataSnapshot", e);
			throw new GoldenNodeRestException(ErrorCode.GENERAL_ERROR);
		}

	}

	private Set<String> getFriends(String userId) {
		Set<String> set = new HashSet<String>();
		List<Friendship> friendships = friendshipRepository.findByUserId(userId);
		for (Friendship friendship : friendships) {
			String friendUserId = friendship.getUserIdFriend();
			set.add(friendUserId);
		}
		return set;

	}

}
