package com.company.sailorsmarketplace.rest;

import com.company.sailorsmarketplace.HK2toGuiceModule;
import com.company.sailorsmarketplace.exceptions.AlreadyExistsExceptionHandler;
import com.company.sailorsmarketplace.exceptions.NotFoundExceptionHandler;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.company.sailorsmarketplace.Module;
import org.glassfish.jersey.server.ApplicationHandler;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        packages(JerseyConfig.class.getPackage().getName());

//        register(Guice.createInjector((Module) binder -> {
//            binder.bind(IUserService.class).to(UserService.class);
//            binder.bind(Database.class).to(UserDAO.class);
//        }));
        Injector injector = Guice.createInjector(new Module());
        HK2toGuiceModule hk2Module = new HK2toGuiceModule(injector);
        register(hk2Module);
//        public Set<Class<?>> getClasses(){
//            return new HashSet<Class<?>>(Arrays.asList(UsersResource.class, NotFoundExceptionHandler.class, AlreadyExistsExceptionHandler.class));
//        }
    }
}