package com.company.sailorsmarketplace.dao;

import com.company.sailorsmarketplace.dbmodel.UsersEntity;
import com.company.sailorsmarketplace.hbutil.HibernateUtils;
import com.google.inject.Singleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

//@Singleton
public class MySQLDAO implements Database {
    private Session session;
    public MySQLDAO() {}

    @Override
    public void openConnection() {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        this.session = sessionFactory.openSession();
    }

    @Override
    public UsersEntity saveUserProfile(UsersEntity userProfile) {
        this.session.beginTransaction();
        this.session.save(userProfile);
        this.session.getTransaction().commit();
        return userProfile;
    }

    @Override
    public void closeConnection() {
        this.session.close();
    }
}
