package com.goldennode.commons.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.goldennode.commons.util.DateTimeUtils;
import com.goldennode.commons.util.UUID;

@Entity
@Table(name = "ifttt_action")
public class IftttAction {
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
	@Column(name = "ifttt_source_id", length = 50, nullable = true)
	private String iftttSourceId;
	@Column(name = "ifttt_source_url", length = 255, nullable = true)
	private String iftttSourceUrl;
	@Column(name = "user_timezone", length = 255, nullable = true)
	private String userTimezone;

	@Column(name = "processed", nullable = false)
	private int processed;

	public IftttAction() {
		super();
	}

	private IftttAction(String id) {
		this.id = id;
		this.processed = 0;
		this.timestamp = DateTimeUtils.getGmtUnixTimestamp();
	}

	public static IftttAction newEntity() {
		return new IftttAction(UUID.getUUID());
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

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getIftttSourceId() {
		return iftttSourceId;
	}

	public void setIftttSourceId(String iftttSourceId) {
		this.iftttSourceId = iftttSourceId;
	}

	public String getIftttSourceUrl() {
		return iftttSourceUrl;
	}

	public void setIftttSourceUrl(String iftttSourceUrl) {
		this.iftttSourceUrl = iftttSourceUrl;
	}

	public String getUserTimezone() {
		return userTimezone;
	}

	public void setUserTimezone(String userTimezone) {
		this.userTimezone = userTimezone;
	}

	public int getProcessed() {
		return processed;
	}

	public void setProcessed(int processed) {
		this.processed = processed;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
