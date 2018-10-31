package com.thingabled.server.controllers.rest.json;

import com.thingabled.commons.entity.ThingData;

public class SocialUserThingDataSnapshot{
	
	String userId;
	String name;
	String email;
	ThingData thingData;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ThingData getThingData() {
		return thingData;
	}
	public void setThingData(ThingData thingData) {
		this.thingData = thingData;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
