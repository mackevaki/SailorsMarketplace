package com.company.sailorsmarketplace.security;

import com.company.sailorsmarketplace.security.GrantedAuthority;

public class UserDetails {

    private String email;
    private String password;
    private boolean enabled;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;
    private boolean accountNonLocked;
    private GrantedAuthority authority;

    public UserDetails(String email, String password, boolean enabled, boolean accountNonExpired,
                       boolean credentialsNonExpired, boolean accountNonLocked, GrantedAuthority authority) {

    }
}
