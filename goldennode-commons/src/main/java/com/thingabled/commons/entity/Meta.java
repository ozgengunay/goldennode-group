package com.thingabled.commons.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.thingabled.commons.util.UUID;

@Entity
@Table(name = "meta")
public class Meta extends BaseEntity {

	@Column(name = "name", length = 20, nullable = false)
	private String name;

	public Meta() {
		super();
	}

	private Meta(String id, Status status) {
		super(id, status);
	}

	public static Meta newEntity() {
		return new Meta(UUID.getUUID(), Status.ENABLED);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
