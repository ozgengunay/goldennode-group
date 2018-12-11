package com.goldennode.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.goldennode.commons.util.UUID;

@Entity
@Table(name = "users")
public class Users {
    @Id
    @Column(name = "id", length = 50, nullable = false, unique = true)
    private String id;
    @Column(name = "email", length = 50, nullable = true, unique = true)
    private String email;
    @Column(name = "firstname", length = 255, nullable = true)
    private String firstName;
    @Column(name = "lastname", length = 255, nullable = true)
    private String lastName;
    @Column(name = "username", length = 50, nullable = true, unique = true)
    private String username;
    @Column(name = "password", length = 255, nullable = true)
    private String password;
    @Column(name = "enabled", nullable = false)
    private int enabled;
    @Column(name = "apikey", length = 50, nullable = true, unique = true)
    private String apiKey;
    @Column(name = "secretkey", length = 50, nullable = true)
    private String secretKey;

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
        ROLE_PREMIUM_USER, ROLE_FREEMIUM_USER, ROLE_TEMP_USER
    }

    public String getId() {
        return id;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

   
}
