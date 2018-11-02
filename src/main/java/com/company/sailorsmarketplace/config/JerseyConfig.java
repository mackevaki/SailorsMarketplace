package com.company.sailorsmarketplace.config;

import com.company.sailorsmarketplace.exceptions.*;
import com.company.sailorsmarketplace.rest.AuthenticationResource;
import com.company.sailorsmarketplace.rest.EventResource;
import com.company.sailorsmarketplace.rest.UserProfileInfoResource;
import com.company.sailorsmarketplace.rest.UsersResource;
import com.company.sailorsmarketplace.services.AuthenticationService;
import com.company.sailorsmarketplace.utils.AuthenticationFilter;
import com.company.sailorsmarketplace.utils.TestValues;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {

        // Resources
        register(UsersResource.class);
        register(AuthenticationResource.class);
        register(UserProfileInfoResource.class);
        register(EventResource.class);

        // Exception handlers
        register(AlreadyExistsExceptionMapper.class);
        register(UserNotFoundExceptionMapper.class);
        register(AuthenticationExceptionMapper.class);
        register(ConstraintViolationMapper.class);

        // Filters
        register(AuthenticationFilter.class);

        // Injectors
        Injector injector = Guice.createInjector(new Module());
        HK2toGuiceModule hk2Module = new HK2toGuiceModule(injector);
        register(hk2Module);
    }
}