package com.company.sailorsmarketplace.config;

import com.company.sailorsmarketplace.services.*;
import com.google.inject.Injector;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class HK2toGuiceModule extends AbstractBinder {
    private Injector guiceInjector;

    public HK2toGuiceModule(Injector guiceInjector) {
        this.guiceInjector = guiceInjector;
    }

    @Override
    protected void configure() {
        bindFactory(new ServiceFactory<UserService>(guiceInjector, UserService.class)).to(UserService.class);
        bindFactory(new ServiceFactory<AuthenticationService>(guiceInjector, AuthenticationService.class)).to(AuthenticationService.class);
        bindFactory(new ServiceFactory<UserProfileInfoService>(guiceInjector, UserProfileInfoService.class)).to(UserProfileInfoService.class);
        bindFactory(new ServiceFactory<EventService>(guiceInjector, EventService.class)).to(EventService.class);
        bindFactory(new ServiceFactory<VerificationService>(guiceInjector, VerificationService.class)).to(VerificationService.class);
    }

    private static class ServiceFactory<T> implements Factory<T> {

        private final Injector guiceInjector;

        private final Class<T> serviceClass;

        public ServiceFactory(Injector guiceInjector, Class<T> serviceClass) {
            this.guiceInjector = guiceInjector;
            this.serviceClass = serviceClass;
        }

        @Override
        public T provide() {
            return guiceInjector.getInstance(serviceClass);
        }

        @Override
        public void dispose(T versionResource) {
        }
    }
}
