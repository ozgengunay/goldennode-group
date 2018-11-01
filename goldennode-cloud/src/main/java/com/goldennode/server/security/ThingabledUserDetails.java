package com.goldennode.server.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.security.SocialUser;

import com.thingabled.commons.entity.Thing;
import com.thingabled.commons.entity.Users.SocialMediaService;

public class ThingabledUserDetails extends SocialUser {

	private String id;

	private String firstName;

	private String lastName;

	private SocialMediaService socialSignInProvider;

	private Thing thing;

	public ThingabledUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password==null?"SocialUser":password, authorities);	
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Collection<GrantedAuthority> getRoles() {
		return super.getAuthorities();
		
	}
	

	public SocialMediaService getSocialSignInProvider() {
		return socialSignInProvider;
	}


	public void setSocialSignInProvider(SocialMediaService socialSignInProvider) {
		this.socialSignInProvider = socialSignInProvider;
	}

	public Thing getThing() {
		return thing;
	}

	public void setThing(Thing thing) {
		this.thing = thing;
	}

	

}
