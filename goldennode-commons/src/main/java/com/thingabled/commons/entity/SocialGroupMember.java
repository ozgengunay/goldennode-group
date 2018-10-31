package com.thingabled.commons.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.thingabled.commons.util.UUID;

@Entity
@Table(name = "socialgroupmember",uniqueConstraints=
@UniqueConstraint(columnNames = {"useridmember", "socialgroupid"}))
public class SocialGroupMember extends BaseEntity {

	@Column(name = "useridmember", length = 50, nullable = false)
	private String userIdMember;

	@Column(name = "socialgroupid", length = 50, nullable = false)
	private String socialGroupId;
	
	public SocialGroupMember() {
		super();
	}

	private SocialGroupMember(String id, Status status) {
		super(id, status);
	}

	public static SocialGroupMember newEntity() {
		return new SocialGroupMember(UUID.getUUID(), Status.ENABLED);
	}

	
	public String getSocialGroupId() {
		return socialGroupId;
	}

	public void setSocialGroupId(String socialGroupId) {
		this.socialGroupId = socialGroupId;
	}

	public String getUserIdMember() {
		return userIdMember;
	}

	public void setUserIdMember(String userIdMember) {
		this.userIdMember = userIdMember;
	}

	
	
}
