package com.thingabled.commons.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thingabled.commons.util.UUID;

@Entity
@Table(name = "thingpoint",uniqueConstraints=
@UniqueConstraint(columnNames = {"internalid", "thingid"}))
public class ThingPoint extends BaseEntity {

	public enum Type {
		DIGITAL_INPUT, DIGITAL_OUTPUT, ANALOG_INPUT, ANALOG_OUTPUT
	}

	public enum Permission {
		PRIVATE, FRIENDS, PUBLIC, GROUP
	}

	@Column(name = "internalid", length = 50, nullable = true)
	private String internalId;
	@Column(name = "name", length = 255, nullable = false)
	private String name;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "thingid", nullable = false)
	private Thing thing;
	@Column(name = "thingid", nullable = false, insertable = false, updatable = false)
	private String thingId;

	@Enumerated(EnumType.STRING)
	@Column(name = "permission", length = 20, nullable = false)
	private Permission permission;

	@Enumerated(EnumType.STRING)
	@Column(name = "type", length = 20, nullable = false)
	private Type type;
	@Column(name = "unit", columnDefinition = "TEXT", nullable = true)
	private String unit;

	public ThingPoint() {
		super();
	}

	private ThingPoint(String id, Status status) {
		super(id, status);
	}

	public static ThingPoint newEntity() {
		return new ThingPoint(UUID.getUUID(), Status.ENABLED);
	}

	public void setThing(Thing thing) {
		this.thing = thing;
		if (!thing.getThingPoints().contains(this)) {
			thing.getThingPoints().add(this);
			thingId = thing.getId();
		}
	}

	public Thing getThing() {
		return thing;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getInternalId() {
		return internalId;
	}

	public void setInternalId(String internalId) {
		this.internalId = internalId;
	}

	public String getThingId() {
		return thingId;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

}
