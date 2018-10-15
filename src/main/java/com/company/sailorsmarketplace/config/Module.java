package com.company.sailorsmarketplace.config;

import com.company.sailorsmarketplace.dao.Database;
import com.company.sailorsmarketplace.dao.UserDAO;
import com.company.sailorsmarketplace.dao.UserProfileInfoDAO;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.exceptions.AuthenticationException;
import com.company.sailorsmarketplace.services.*;
import com.company.sailorsmarketplace.utils.TestValues;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;

public class Module extends AbstractModule {

    @Override
    protected void configure() {
        //bind the services to implementation class
        bind(IAuthenticationService.class).to(AuthenticationService.class);
        bind(IUserService.class).to(UserService.class);
        bind(Database.class).to(UserDAO.class);
        bind(IUserProfileInfoService.class).to(UserProfileInfoService.class);
        bind(IEventService.class).to(EventService.class);
        bind(TestValues.class);
    }
}