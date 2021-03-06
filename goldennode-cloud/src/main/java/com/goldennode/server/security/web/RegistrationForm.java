package com.goldennode.server.security.web;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.goldennode.server.security.web.validation.PasswordsNotEmpty;
import com.goldennode.server.security.web.validation.PasswordsNotEqual;

@PasswordsNotEmpty(passwordFieldName = "password", passwordVerificationFieldName = "passwordVerification")
@PasswordsNotEqual(passwordFieldName = "password", passwordVerificationFieldName = "passwordVerification")
public class RegistrationForm {
    public static final String FIELD_NAME_EMAIL = "email";
    @Email
    @NotEmpty
    @Size(max = 100)
    private String email;
    @NotEmpty
    @Size(max = 100)
    private String firstName;
    @NotEmpty
    @Size(max = 100)
    private String lastName;
    private String password;
    private String passwordVerification;

    public RegistrationForm() {
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

    public String getPasswordVerification() {
        return passwordVerification;
    }

    public void setPasswordVerification(String passwordVerification) {
        this.passwordVerification = passwordVerification;
    }
}
