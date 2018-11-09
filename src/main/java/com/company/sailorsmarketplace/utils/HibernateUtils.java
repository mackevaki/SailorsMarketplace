package com.company.sailorsmarketplace.utils;

import com.company.sailorsmarketplace.dbmodel.*;
import com.mysql.cj.Messages;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.exceptions.InvalidConnectionAttributeException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HibernateUtils {
    private static final Logger log = LoggerFactory.getLogger(HibernateUtils.class);
    private static SessionFactory sessionFactory;
    static {
        try {
            sessionFactory = buildSessionFactory();
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
    }
    private HibernateUtils() { throw new AssertionError(); }

    private static SessionFactory buildSessionFactory() throws IOException {
        // A SessionFactory is set up once for an application!
        Configuration configuration = new Configuration();
        InputStream inputStream = HibernateUtils.class.getClassLoader().
            getResourceAsStream("hibernate-configuration.yml");
        if (inputStream == null) {
            throw ExceptionFactory.createException(InvalidConnectionAttributeException.class,
                                                   Messages.getString("ConnectionString.10", new Object[] {"hibernate-configuration.yml"}));
        }
        Properties hibernateProperties = new Properties();
        hibernateProperties.load(inputStream);
        configuration.setProperties(hibernateProperties);
        configuration.addPackage("com.company.sailorsmarketplace.dbmodel")
            .addAnnotatedClass(User.class)
            .addAnnotatedClass(UserProfileInfo.class)
            .addAnnotatedClass(VerificationCode.class)
            .addAnnotatedClass(AdminsRoles.class)
            .addAnnotatedClass(Event.class)
            .addAnnotatedClass(Organization.class)
            .addAnnotatedClass(TelegramConnection.class);

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        try {
            sessionFactory = configuration.buildSessionFactory(registry);
        } catch (ExceptionInInitializerError e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );
            throw new ExceptionInInitializerError("Building session factory failed: " + e.getMessage());
        }
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }
}
