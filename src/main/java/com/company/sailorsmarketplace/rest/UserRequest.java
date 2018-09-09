package com.company.sailorsmarketplace.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserRequest {
    @Size(max = 20, min = 3, message = "Invalid Username")
    @NotEmpty(message = "Please enter username")
    public final String username;

    @NotEmpty
    @Size(min=8, max = 20, message = "Password must be between 8 and 20 characters")
    public final String password;

    @Email(message = "Invalid Email")
    @NotEmpty(message = "Please enter email")
    public final String email;


    @Size(max = 12, message = "Must be between less 12 characters")
    @NotEmpty(message = "Please enter password")
    public final String telephone;
    public final String salt;
    public final Long id;
    public final Byte enabled;

    @JsonCreator
    public UserRequest(
            @JsonProperty("username") String username,
            @JsonProperty("password") String password,
            @JsonProperty("email") String email,
            @JsonProperty("telephone") String telephone,
            @JsonProperty("salt") String salt,
            @JsonProperty("id") Long id,
            @JsonProperty("enabled") Byte enabled) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.telephone = telephone;
        this.salt = salt;
        this.id = id;
        this.enabled = enabled;
    }
}
