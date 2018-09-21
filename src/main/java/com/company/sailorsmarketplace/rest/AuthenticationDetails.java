package com.company.sailorsmarketplace.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AuthenticationDetails {
    private Long id;
    private String token;

    public AuthenticationDetails() {}

    public AuthenticationDetails(
            Long id,
            String token) {
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
