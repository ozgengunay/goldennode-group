package com.goldennode.commons.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.goldennode.commons.util.UUID;

@Entity
@Table(name = "thingownership")
public class ThingOwnership extends BaseEntity {


	@Column(name = "thingid", length = 50, nullable = false)
	private String thingId;
	@Column(name = "userid", length = 50, nullable = false)
	private String userId;

	public ThingOwnership() {
		super();
	}

	private ThingOwnership(String id, Status status) {
		super(id, status);
	}

	public static ThingOwnership newEntity() {
		return new ThingOwnership(UUID.getUUID(), Status.ENABLED);
	}

	public String getThingId() {
		return thingId;
	}

	public void setThingId(String thingId) {
		this.thingId = thingId;
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


}
