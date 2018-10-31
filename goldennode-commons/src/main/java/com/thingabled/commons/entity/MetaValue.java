package com.thingabled.commons.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.thingabled.commons.util.UUID;

@Entity
@Table(name = "metavalue",uniqueConstraints=
@UniqueConstraint(columnNames = {"fieldid", "metaid"}))
public class MetaValue extends BaseEntity {

	@Column(name = "fieldid", length = 50, nullable = false)
	private String fieldId;
	@Column(name = "metaid", length = 50, nullable = false)
	private String metaId;
	@Column(name = "value", columnDefinition = "TEXT", nullable = false)
	private String value;

	public MetaValue() {
		super();
	}

	private MetaValue(String id, Status status) {
		super(id, status);
	}

	public static MetaValue newEntity() {
		return new MetaValue(UUID.getUUID(), Status.ENABLED);
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getMetaId() {
		return metaId;
	}

	public void setMetaId(String metaId) {
		this.metaId = metaId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
