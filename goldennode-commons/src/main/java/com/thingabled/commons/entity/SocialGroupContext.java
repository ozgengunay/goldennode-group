package com.thingabled.commons.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.thingabled.commons.util.UUID;

@Entity
@Table(name = "socialgroupcontext",uniqueConstraints=
@UniqueConstraint(columnNames = {"thingcontextid", "socialgroupid"}))
public class SocialGroupContext extends BaseEntity {

	@Column(name = "thingcontextid", length = 50, nullable = false)
	private String thingContextId;

	@Column(name = "socialgroupid", length = 50, nullable = false)
	private String socialGroupId;
	
	public SocialGroupContext() {
		super();
	}

	private SocialGroupContext(String id, Status status) {
		super(id, status);
	}

	public static SocialGroupContext newEntity() {
		return new SocialGroupContext(UUID.getUUID(), Status.ENABLED);
	}

	
	public String getSocialGroupId() {
		return socialGroupId;
	}

	public void setSocialGroupId(String socialGroupId) {
		this.socialGroupId = socialGroupId;
	}

	public String getThingContextId() {
		return thingContextId;
	}

	public void setThingContextId(String thingContextId) {
		this.thingContextId = thingContextId;
	}

	
	
	
}
