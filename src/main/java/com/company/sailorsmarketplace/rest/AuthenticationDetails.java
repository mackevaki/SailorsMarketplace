package com.company.sailorsmarketplace.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationDetails {
    private Long id;
    private String token;

    public AuthenticationDetails() {}

    @JsonCreator
    public AuthenticationDetails(
            @JsonProperty("id") Long id,
            @JsonProperty("token") String token
    ) {
        this.id = id;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
