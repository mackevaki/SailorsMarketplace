package com.company.sailorsmarketplace.utils;

import com.company.sailorsmarketplace.dbmodel.*;
import com.mysql.cj.Messages;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.exceptions.InvalidConnectionAttributeException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static java.lang.System.out;

public class HibernateUtils {
    private static SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = buildSessionFactory();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private HibernateUtils() {}

    protected static SessionFactory buildSessionFactory() throws IOException {
        // A SessionFactory is set up once for an application!
        Configuration configuration = new Configuration();
        InputStream inputStream = HibernateUtils.class.getClassLoader().
            getResourceAsStream("config.yml");
        if (inputStream == null) {
            throw ExceptionFactory.createException(InvalidConnectionAttributeException.class,
                                                   Messages.getString("ConnectionString.10", new Object[] {"config.yml"}));
        }
        Properties hibernateProperties = new Properties();
        hibernateProperties.load(inputStream);
        configuration.setProperties(hibernateProperties);
        // Настройки hibernate
//        Configuration configuration = new Configuration()
//                .setProperty( "hibernate.dialect",
//                             "org.hibernate.dialect.MySQLDialect" )
//                .setProperty( "hibernate.connection.driver_class",
//                              "com.mysql.cj.jdbc.Driver" )
//                .setProperty( "hibernate.connection.url",
//                              "jdbc:mysql://localhost:3306/smarket?useLegacyDatetimeCode=false&serverTimezone=UTC&autoReconnect=true" )
//                .setProperty( "hibernate.connection.username",
//                              "smarket" )
//                .setProperty( "hibernate.connection.password",
//                              "smarket" )
//                .setProperty( "hibernate.connection.pool_size",
//                              "10" )
//                .setProperty( "hibernate.connection.autocommit",
//                              "false" )
//                .setProperty( "hibernate.cache.provider_class",
//                              "org.hibernate.cache.NoCacheProvider" )
//                .setProperty( "hibernate.cache.use_second_level_cache",
//                              "false" )
//                .setProperty( "hibernate.hbm2ddl.auto",
//                              "create-drop" )
//                .setProperty( "hibernate.cache.use_query_cache",
//                              "false" )
//                .setProperty( "hibernate.show_sql",
//                              "true" )
//                .setProperty( "hibernate.query.factory_class",
//                             "org.hibernate.hql.internal.classic.ClassicQueryTranslatorFactory" )
//                .setProperty( "hibernate.current_session_context_class",
//                              "thread" )
//                .setProperty("hibernate.jdbc.lob.non_contextual_creation",
//                             "true" )
//                .setProperty("hibernate.id.new_generator_mappings", "true")
                configuration.addPackage("com.company.sailorsmarketplace.dbmodel")
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(UserProfileInfo.class)
                .addAnnotatedClass(VerificationCode.class)
                .addAnnotatedClass(AdminsRoles.class)
                .addAnnotatedClass(Event.class)
                .addAnnotatedClass(Organization.class)
                .addAnnotatedClass(TelegramConnection.class);

                out.println(configuration.getProperties().toString());
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        try {
            sessionFactory = configuration.buildSessionFactory(registry);
        } catch (ExceptionInInitializerError e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );

            throw new RuntimeException("Building session factory failed: " + e.getMessage(), e);
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
