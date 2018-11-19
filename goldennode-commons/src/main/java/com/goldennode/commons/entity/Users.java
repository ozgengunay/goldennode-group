package com.goldennode.commons.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.goldennode.commons.util.UUID;

@Entity
@Table(name = "users")
public class Users {
    @Id
    @Column(name = "id", length = 50)
    private String id;
    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;
    @Column(name = "first_name", length = 255, nullable = false)
    private String firstName;
    @Column(name = "last_name", length = 255, nullable = false)
    private String lastName;
    @Column(name = "username", length = 255, nullable = false, unique = true)
    private String username;
    @Column(name = "password", length = 255, nullable = true)
    private String password;
    @Column(name = "enabled", nullable = false)
    private int enabled;

    public Users() {
        super();
    }

    private Users(String id) {
        this.id = id;
    }

    public static Users newEntity() {
        return new Users(UUID.getUUID());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public enum Role {
        ROLE_USER, ROLE_CLIENT
    }

    public String getId() {
        return id;
    }
}
