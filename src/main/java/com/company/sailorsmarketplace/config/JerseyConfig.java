package com.company.sailorsmarketplace.config;

import com.company.sailorsmarketplace.exceptions.AlreadyExistsExceptionMapper;
import com.company.sailorsmarketplace.exceptions.AuthenticationExceptionMapper;
import com.company.sailorsmarketplace.exceptions.ConstraintViolationMapper;
import com.company.sailorsmarketplace.exceptions.UserNotFoundExceptionMapper;
import com.company.sailorsmarketplace.rest.AuthenticationResource;
import com.company.sailorsmarketplace.rest.UsersResource;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        // Resources
        register(UsersResource.class);
        register(AuthenticationResource.class);

        // Exception handlers
        register(AlreadyExistsExceptionMapper.class);
        register(UserNotFoundExceptionMapper.class);
        register(AuthenticationExceptionMapper.class);
        register(ConstraintViolationMapper.class);

        // Injectors
        Injector injector = Guice.createInjector(new Module());
        HK2toGuiceModule hk2Module = new HK2toGuiceModule(injector);
        register(hk2Module);
    }
}