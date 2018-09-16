package com.company.sailorsmarketplace.config;

import com.company.sailorsmarketplace.exceptions.AlreadyExistsExceptionHandler;
import com.company.sailorsmarketplace.exceptions.AuthenticationExceptionHandler;
import com.company.sailorsmarketplace.exceptions.ConstraintViolationHandler;
import com.company.sailorsmarketplace.exceptions.NotFoundExceptionHandler;
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
        register(AlreadyExistsExceptionHandler.class);
        register(NotFoundExceptionHandler.class);
        register(AuthenticationExceptionHandler.class);
        register(ConstraintViolationHandler.class);

        // Injectors
        Injector injector = Guice.createInjector(new Module());
        HK2toGuiceModule hk2Module = new HK2toGuiceModule(injector);
        register(hk2Module);
    }
}