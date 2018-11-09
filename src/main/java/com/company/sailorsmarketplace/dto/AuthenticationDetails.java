package com.company.sailorsmarketplace.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationDetails {
    public final Long id;
    public final String token;

    @JsonCreator
    public AuthenticationDetails(
            @JsonProperty("id") Long id,
            @JsonProperty("token") String token) {
        this.id = id;
        this.token = token;
    }

    @Override
    public String toString() {
        return "AuthenticationDetails{" +
            "id=" + id +
            ", token='" + token + '\'' +
            '}';
    }
}
