package com.thingabled.commons.entity;

import java.util.Date;

public class ThingData  {

	private String thingPointId;

	private Date time;

	private String value;

	private Double longitude;

	private Double latitude;

	public ThingData() {

	}



	public String getThingPointId() {
		return thingPointId;
	}

	public void setThingPointId(String thingPointId) {
		this.thingPointId = thingPointId;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

}
