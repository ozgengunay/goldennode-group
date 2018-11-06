package com.goldennode.commons.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.goldennode.commons.util.UUID;

@Entity
@Table(name = "socialgroup")
public class SocialGroup extends BaseEntity {

	@Column(name = "name", length = 255, nullable = false, unique = true)
	private String name;
	@Column(name = "userid", length = 50, nullable = false)
	private String userId;

	public SocialGroup() {
		super();
	}

	private SocialGroup(String id, Status status) {
		super(id, status);
	}

	public static SocialGroup newEntity() {
		return new SocialGroup(UUID.getUUID(), Status.ENABLED);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
