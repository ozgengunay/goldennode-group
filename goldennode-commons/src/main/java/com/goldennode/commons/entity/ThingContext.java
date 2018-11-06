package com.goldennode.commons.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import com.goldennode.commons.util.UUID;

@Entity
@Table(name = "thingcontext")
public class ThingContext extends BaseEntity {
	public enum Type {
		CONTEXT, VIRTUAL, PHYSICAL
	}

	@Column(name = "name", length = 255, nullable = false)
	private String name;
	@Column(name = "parent", length = 50, nullable = true)
	private String parent;
	@Enumerated(EnumType.STRING)
	@Column(name = "type", length = 20, nullable = false)
	private Type type;
	@Column(name = "userid", length = 50, nullable = false)
	private String userId;

	public ThingContext() {
		super();
	}

	private ThingContext(String id, Status status) {
		super(id, status);
	}

	public static ThingContext newEntity() {
		return new ThingContext(UUID.getUUID(), Status.ENABLED);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
