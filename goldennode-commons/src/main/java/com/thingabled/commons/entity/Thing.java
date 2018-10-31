package com.thingabled.commons.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thingabled.commons.util.UUID;

@Entity
@Table(name = "thing")
public class Thing extends BaseEntity {

	@Column(name = "thingcontextid", length = 50, nullable = false)
	private String thingContextId;
	@Column(name = "publickey", length = 50, unique = true, nullable = true)
	private String publickey;
	@JsonIgnore
	@Column(name = "secretkey", length = 50, nullable = true)
	private String secretkey;
	@OneToMany(mappedBy = "thing", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<ThingPoint> thingPoints = new ArrayList<ThingPoint>();
	@Column(name = "longitude", nullable = true)
	private Double longitude;
	@Column(name = "latitude", nullable = true)
	private Double latitude;
	public Thing() {
		super();
	}

	private Thing(String id, Status status) {
		super(id, status);
	}

	public static Thing newEntity() {
		return new Thing(UUID.getUUID(), Status.ENABLED);
	}

	public void addThingPoint(ThingPoint thingPoint) {
		thingPoints.add(thingPoint);
		if (thingPoint.getThing() != this) {
			thingPoint.setThing(this);
		}
	}

	public List<ThingPoint> getThingPoints() {
		return thingPoints;
	}

	public String getThingContextId() {
		return thingContextId;
	}

	public void setThingContextId(String thingContextId) {
		this.thingContextId = thingContextId;
	}

	public String getPublickey() {
		return publickey;
	}

	public void setPublickey(String publickey) {
		this.publickey = publickey;
	}

	public String getSecretkey() {
		return secretkey;
	}

	public void setSecretkey(String secretkey) {
		this.secretkey = secretkey;
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
