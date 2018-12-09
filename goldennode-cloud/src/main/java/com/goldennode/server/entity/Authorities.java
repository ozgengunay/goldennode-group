package com.goldennode.server.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "authorities", uniqueConstraints = @UniqueConstraint(columnNames = { "userid", "authority" }))
public class Authorities {
    @Id
    @Column(name = "id", length = 50)
    private String id;
    @Column(name = "userid", length = 50, nullable = false)
    private String userId;
    @Column(name = "authority", length = 50, nullable = false)
    private String authority;

    public Authorities() {
        super();
    }

    private Authorities(String id) {
        this.id = id;
    }

    public static Authorities newEntity() {
        return new Authorities(UUID.randomUUID().toString());
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
