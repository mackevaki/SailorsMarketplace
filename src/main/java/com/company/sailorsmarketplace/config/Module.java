package com.company.sailorsmarketplace.config;

import com.company.sailorsmarketplace.dao.Database;
import com.company.sailorsmarketplace.dao.UserDAO;
import com.company.sailorsmarketplace.exceptions.AuthenticationException;
import com.company.sailorsmarketplace.services.*;
import com.google.inject.AbstractModule;

public class Module extends AbstractModule {

    @Override
    protected void configure() {

        //bind the services to implementation class
        bind(IAuthenticationService.class).to(AuthenticationService.class);
        bind(IUserService.class).to(UserService.class);
        bind(Database.class).to(UserDAO.class);
        bind(IUserDetailsService.class).to(UserDetailsService.class);
        bind(IUserProfileInfoService.class).to(UserProfileInfoService.class);
    }
}