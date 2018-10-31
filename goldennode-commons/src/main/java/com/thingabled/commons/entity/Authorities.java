package com.thingabled.commons.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.thingabled.commons.util.UUID;

@Entity
@Table(name = "authorities",uniqueConstraints=
@UniqueConstraint(columnNames = {"username", "authority"}))
public class Authorities {
	
	
	@Id
	@Column(name = "id", length = 50)
	private String id;


	@Column(name = "username", length = 50, nullable = false)
	private String username;

	@Column(name = "authority", length = 50, nullable = false)
	private String authority;

	

	public Authorities() {
		super();
	}

	private Authorities(String id) {
		this.id=id;
	}

	public static Authorities newEntity() {
		return new Authorities(UUID.getUUID());
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getId() {
		return id;
	}


}
