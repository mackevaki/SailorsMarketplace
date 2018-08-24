package com.company.sailorsmarketplace.hbutil;

import com.company.sailorsmarketplace.dbmodel.UsersEntity;
import com.company.sailorsmarketplace.dbmodel.UsersProfilesEntity;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
    private static final SessionFactory sessionFactory;

    static {
        Configuration conf = new Configuration();
        //Это нам нужно для того, чтобы мы добавили все наши классы сущности.
        //каждый ваш Entity здесь нужно прописать, не пропишете - не будет работать.
        conf.addAnnotatedClass(UsersEntity.class).addAnnotatedClass(UsersProfilesEntity.class);
        conf.configure();

        try {
            sessionFactory = conf.buildSessionFactory();
            //Вот мы собственно и создали нашу Фабрику сессий.
            //Она нужна т.к с БД мы работаем через сессии
        } catch (HibernateException e) {
            System.err.println("Initial SessionFactory creation failed." + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
