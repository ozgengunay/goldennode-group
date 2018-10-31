package com.thingabled.server.controllers.rest;

public enum ErrorCode{
	CAN_REGISTER_ONLY_PHYSICAL_OR_VIRTUAL_THINGS("canregisteronlyphysicalorvirtualthings") ,
	CAN_NOT_DISOWN_VIRTUAL_THINGS("cannotdisownvirtualthings") ,
	THING_ALREADY_OWNED("thingalreadyowned") ,
	CANT_DEREGISTER_PHYSICAL_THINGS("cannotderegisterphysicalthings") ,
	THING_NOT_OWNED("thingnotowned") ,
	THING_CONTEXT_NOT_FOUND("thingcontextnotfound") ,
	THING_NOT_FOUND("thingnotfound") ,
	SAME_THING_ALREADY_REGISTERED("samethingalreadyregistered") ,
	THING_POINT_NOT_FOUND("Thingpointnotfound") ,
	CAN_NOT_ADD_POINT_TO_PHYSICAL_THINGS("cannotaddpointtophysicalthings") ,
	THING_POINT_ALREADY_EXISTS("thingpointalreadyexists") ,
	CAN_NOT_REMOVE_POINT_FROM_PHYSICAL_THINGS("cannotremovepointfromphysicalthings") ,
	ENTITY_NOT_FOUND("entity not found") ,
	META_NOT_FOUND("meta not found") ,
	META_VALUE_NOT_FOUND("meta value not found") ,
	LOCATION_NOT_FOUND("location not found"),
	GENERAL_ERROR("general error"), 
	REGISTERATIONNOTALLOWEDFORNONDEVELOPERS("thing registration not allowed for non-developers");

	private final String name;

	private ErrorCode(String s) {
		name = s;
	}

	public boolean equalsName(String otherName) {
		return (otherName == null) ? false : name.equals(otherName);
	}

	public String toString() {
		return this.name;
	}

}