package com.company.sailorsmarketplace.services;

import com.company.sailorsmarketplace.dao.Database;
import com.company.sailorsmarketplace.security.GrantedAuthority;
import com.company.sailorsmarketplace.security.SimpleGrantedAuthority;
import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class UserDetailsService implements IUserDetailsService {
    @Inject
    private Database database;
//
//    public UserDetails loadUserByLogin(String email)throws UserNotFoundException {
//        User user;
//        if ((user = database.getByEmail(email)) == null) {
//            throw new UserNotFoundException("No user found with email: "+ email);
//        }
//
//        boolean enabled = true;
//        boolean accountNonExpired = true;
//        boolean credentialsNonExpired = true;
//        boolean accountNonLocked = true;
//
//        return  new org.springframework.security.core.userdetails.User
//                (user.getEmail(),
//                        user.getPassword().toLowerCase(), enabled, accountNonExpired,
//                        credentialsNonExpired, accountNonLocked,
//                        getAuthorities(user.getRoles()));
//    }

    private static List<GrantedAuthority> getAuthorities (List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
