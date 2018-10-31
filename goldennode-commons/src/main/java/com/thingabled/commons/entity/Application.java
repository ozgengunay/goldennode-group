package com.thingabled.commons.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.thingabled.commons.util.UUID;

@Entity
@Table(name = "application")
public class Application extends BaseEntity {

	@Column(name = "name", length = 255, nullable = false)
	private String name;
	@Column(name = "userid", length = 50, nullable = false)
	private String userId;

	public Application() {
		super();
	}

	private Application(String id, Status status) {
		super(id, status);
	}

	public static Application newEntity() {
		return new Application(UUID.getUUID(), Status.ENABLED);
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
