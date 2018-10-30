package com.company.sailorsmarketplace.config;

import com.company.sailorsmarketplace.services.*;
import com.company.sailorsmarketplace.utils.TestValues;
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
        bindFactory(new ServiceFactory<IUserService>(guiceInjector, IUserService.class)).to(IUserService.class);
        bindFactory(new ServiceFactory<IAuthenticationService>(guiceInjector, IAuthenticationService.class)).to(IAuthenticationService.class);
        bindFactory(new ServiceFactory<IUserProfileInfoService>(guiceInjector, IUserProfileInfoService.class)).to(IUserProfileInfoService.class);
        bindFactory(new ServiceFactory<IEventService>(guiceInjector, IEventService.class)).to(IEventService.class);
        bindFactory(new ServiceFactory<TestValues>(guiceInjector, TestValues.class)).to(TestValues.class);
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
