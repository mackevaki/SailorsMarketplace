package com.company.sailorsmarketplace.requests;

import com.company.sailorsmarketplace.validators.ValidPassword;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class AuthenticationRequest {
    @Email(message = "Invalid Email")
    @NotEmpty(message = "Email can not be empty")
    public final String email;

    @NotEmpty(message = "Password can not be empty")
    @ValidPassword
    public final String password;

    @JsonCreator
    public AuthenticationRequest(
            @JsonProperty("email") String email,
            @JsonProperty("password") String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "AuthenticationRequest{" +
            "email='" + email + '\'' +
            ", password='" + password + '\'' +
            '}';
    }
}
