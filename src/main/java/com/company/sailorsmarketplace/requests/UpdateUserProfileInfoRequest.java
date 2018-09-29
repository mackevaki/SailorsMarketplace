package com.company.sailorsmarketplace.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.sql.Date;

public class UpdateUserProfileInfoRequest {
    @NotNull
    public final Long userId;

    public final String firstname;
    public final String lastname;
    public final Date birthdate;
    public final String city;
    public final Enum gender;
    public final String organization;
    public final byte[] avatar;

    @JsonCreator
    public UpdateUserProfileInfoRequest(
            @JsonProperty("userId") Long userId,
            @JsonProperty("firstname") String firstname,
            @JsonProperty("lastname") String lastname,
            @JsonProperty("birthdate") Date birthdate,
            @JsonProperty("gender") Enum gender,
            @JsonProperty("city") String city,
            @JsonProperty("organization") String organization,
            @JsonProperty("avatar") byte[] avatar) {
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.gender = gender;
        this.city = city;
        this.organization = organization;
        this.avatar = avatar;
    }
}
