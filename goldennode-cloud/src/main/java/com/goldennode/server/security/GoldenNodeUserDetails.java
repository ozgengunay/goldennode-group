package com.goldennode.server.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.security.SocialUser;
import com.goldennode.commons.entity.Thing;
import com.goldennode.commons.entity.Users.SocialMediaService;

public class GoldenNodeUserDetails extends SocialUser {

	private String id;

	private String firstName;

	private String lastName;

	private SocialMediaService socialSignInProvider;

	private Thing thing;

	public GoldenNodeUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
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
