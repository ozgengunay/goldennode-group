package com.thingabled.commons.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.thingabled.commons.util.UUID;

//META : yahooId,googleId
@Entity
@Table(name = "location")
public class Location extends BaseEntity {
	@Column(name = "userid", length = 50, nullable = false)
	private String userId;
	@Column(name = "name", length = 255, nullable = false)
	private String name;
	@Column(name = "longitude", nullable = false)
	private double longitude;
	@Column(name = "latitude", nullable = false)
	private double latitude;

	public Location() {
		super();
	}

	private Location(String id, Status status) {
		super(id, status);
	}

	public static Location newEntity() {
		return new Location(UUID.getUUID(), Status.ENABLED);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
