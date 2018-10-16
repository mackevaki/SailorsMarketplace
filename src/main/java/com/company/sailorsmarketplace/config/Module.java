package com.company.sailorsmarketplace.config;

import com.company.sailorsmarketplace.dao.*;
import com.company.sailorsmarketplace.dbmodel.Event;
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
        bind(IAuthenticationService.class).to(AuthenticationService.class);
        bind(IUserService.class).to(UserService.class);
        bind(Database.class).to(UserDAO.class);
        bind(IUserProfileInfoService.class).to(UserProfileInfoService.class);
        bind(IEventService.class).to(EventService.class);
        bind(new TypeLiteral<DAO<Event>>(){}).to(EventDAO.class);
        bind(TestValues.class);
    }
}