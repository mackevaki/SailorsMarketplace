package com.company.sailorsmarketplace.config;

import com.company.sailorsmarketplace.dao.EventRepository;
import com.company.sailorsmarketplace.dao.UserProfileInfoRepository;
import com.company.sailorsmarketplace.dao.UserRepository;
import com.company.sailorsmarketplace.services.AuthenticationService;
import com.company.sailorsmarketplace.services.EventService;
import com.company.sailorsmarketplace.services.UserProfileInfoService;
import com.company.sailorsmarketplace.services.UserService;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class Module extends AbstractModule {

    @Override
    protected void configure() {
        bind(AuthenticationService.class).in(Singleton.class);
        bind(UserService.class).in(Singleton.class);
        bind(UserRepository.class).in(Singleton.class);
        bind(UserProfileInfoService.class).in(Singleton.class);
        bind(EventService.class).in(Singleton.class);
        bind(EventRepository.class).in(Singleton.class);
        bind(UserProfileInfoRepository.class).in(Singleton.class);
    }
}