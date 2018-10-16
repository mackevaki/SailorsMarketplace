package com.company.sailorsmarketplace.requests;

import com.company.sailorsmarketplace.validators.ValidPassword;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UpdateUserRequest {

    @NotNull
    public final Long userId;

    @Size(max = 20, min = 3, message = "Invalid Username")
    @NotEmpty(message = "Please enter username")
    public final String username;

    @NotEmpty
    @ValidPassword
    @Size(min=8, max = 30, message = "Password must be between 8 and 20 characters")
    public final String password;

    @NotEmpty
    @ValidPassword
    @Size(min=8, max = 30, message = "Password must be between 8 and 20 characters")
    private final String matchingPassword;

    @Email(message = "Invalid Email")
    @NotEmpty(message = "Please enter email")
    public final String email;


    @Size(max = 12, message = "Must be between less 12 characters")
    @NotEmpty(message = "Please enter password")
    public final String telephone;

    @JsonCreator
    public UpdateUserRequest(
            @JsonProperty("userId") Long userId,
            @JsonProperty("username") String username,
            @JsonProperty("password") String password,
            @JsonProperty("matchingPassword") String matchingPassword,
            @JsonProperty("email") String email,
            @JsonProperty("telephone") String telephone) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.matchingPassword = matchingPassword;
        this.email = email;
        this.telephone = telephone;
    }
}
