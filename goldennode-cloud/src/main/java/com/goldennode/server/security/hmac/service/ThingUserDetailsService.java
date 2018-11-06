package com.goldennode.server.security.hmac.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.goldennode.commons.entity.Thing;
import com.goldennode.commons.entity.ThingOwnership;
import com.goldennode.commons.entity.Users;
import com.goldennode.commons.entity.BaseEntity.Status;
import com.goldennode.commons.repository.ThingOwnershipRepository;
import com.goldennode.commons.repository.ThingRepository;
import com.goldennode.commons.repository.UserRepository;
import com.goldennode.server.security.GoldenNodeUserDetails;

@Service
public class ThingUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ThingRepository thingRepository;
	@Autowired
	private ThingOwnershipRepository thingOwnershipRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Thing thing = thingRepository.findByPublickeyAndStatus(username, Status.ENABLED);
		if (thing == null) {
			throw new UsernameNotFoundException("Thing could not be found with the supplied publicKey");
		}

		ThingOwnership thingOwnership_ = thingOwnershipRepository.findByThingIdAndStatus(thing.getId(),
				Status.ENABLED);
		if (thingOwnership_==null) {
			throw new UsernameNotFoundException("No owner for the thing");
		}

		Users user = userRepository.findById(thingOwnership_.getUserId());
		
		
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(Users.Role.ROLE_PT.toString()));
		GoldenNodeUserDetails principal = new GoldenNodeUserDetails(user.getEmail(), user.getPassword_(), authorities);
		principal.setFirstName(user.getFirstName());
		principal.setId(user.getId());
		principal.setLastName(user.getLastName());
		principal.setSocialSignInProvider(user.getSignInProvider());
		principal.setThing(thing);

		return principal;
	}

}
