package com.thingabled.commons.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.thingabled.commons.util.DateTimeUtils;
import com.thingabled.commons.util.UUID;

@Entity
@Table(name = "ifttt_trigger")
public class IftttTrigger {
	@Id
	@Column(name = "id", length = 50)
	private String id;
	@Column(name = "name", length = 255, nullable = false)
	private String name;
	@Column(name = "userid", length = 50, nullable = false)
	private String userid;
	@Column(name = "data", length = 5000, nullable = true)
	private String data;
	
	@Column(name = "timestamp", nullable = false)
	private long timestamp;

	public IftttTrigger() {
		super();
	}

	private IftttTrigger(String id) {
		this.id = id;
		this.timestamp = DateTimeUtils.getGmtUnixTimestamp();
	}

	public static IftttTrigger newEntity() {
		return new IftttTrigger(UUID.getUUID());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
