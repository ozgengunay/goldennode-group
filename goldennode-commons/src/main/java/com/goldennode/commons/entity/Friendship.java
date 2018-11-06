package com.goldennode.commons.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import com.goldennode.commons.util.UUID;

@Entity
@Table(name = "friendship",uniqueConstraints=
@UniqueConstraint(columnNames = {"useridfriend", "userid"}))
public class Friendship extends BaseEntity {

	@Column(name = "useridfriend", length = 50, nullable = false)
	private String userIdFriend;

	@Column(name = "userid", length = 50, nullable = false)
	private String userId;
	
	@Column(name = "tags", columnDefinition = "TEXT", nullable = true)
	private String tags;

	public Friendship() {
		super();
	}

	private Friendship(String id, Status status) {
		super(id, status);
	}

	public static Friendship newEntity() {
		return new Friendship(UUID.getUUID(), Status.ENABLED);
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserIdFriend() {
		return userIdFriend;
	}

	public void setUserIdFriend(String userIdFriend) {
		this.userIdFriend = userIdFriend;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	

}
