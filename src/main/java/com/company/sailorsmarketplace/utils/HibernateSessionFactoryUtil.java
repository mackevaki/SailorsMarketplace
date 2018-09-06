package com.company.sailorsmarketplace.utils;

import com.company.sailorsmarketplace.dbmodel.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration conf = new Configuration().configure("/hibernate.cfg.xml");
                conf.addAnnotatedClass(UsersEntity.class);
                conf.addAnnotatedClass(UsersProfilesEntity.class);
                conf.addAnnotatedClass(VerificationCodesEntity.class);
                conf.addAnnotatedClass(TelegramConnectionsEntity.class);
                conf.addAnnotatedClass(ParticipationsEntity.class);
                conf.addAnnotatedClass(OrganizationsEntity.class);
                conf.addAnnotatedClass(EventsEntity.class);
                conf.addAnnotatedClass(AdminsRolesEntity.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(conf.getProperties());
                sessionFactory = conf.buildSessionFactory(builder.build());

            } catch (Exception e) {
                throw new RuntimeException("Error", e);
            }
        }
        return sessionFactory;
    }
}