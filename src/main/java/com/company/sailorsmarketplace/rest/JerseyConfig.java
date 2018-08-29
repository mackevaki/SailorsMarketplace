package com.company.sailorsmarketplace.rest;

import com.company.sailorsmarketplace.HK2toGuiceModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.company.sailorsmarketplace.Module;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        packages(JerseyConfig.class.getPackage().getName());

//        register(Guice.createInjector((Module) binder -> {
//            binder.bind(IAccountService.class).to(AccountService.class);
//            binder.bind(Database.class).to(UserDAO.class);
//        }));
        Injector injector = Guice.createInjector(new Module());
        HK2toGuiceModule hk2Module = new HK2toGuiceModule(injector);
        register(hk2Module);
    }
}