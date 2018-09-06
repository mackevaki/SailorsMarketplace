package com.company.sailorsmarketplace.dao;

import com.company.sailorsmarketplace.dbmodel.UsersEntity;
import com.company.sailorsmarketplace.utils.HibernateSessionFactoryUtil;
import com.company.sailorsmarketplace.utils.HibernateUtils;
import com.company.sailorsmarketplace.utils.JPAUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

//@Singleton
public class UserDAO implements Database {
    @PersistenceContext(unitName = "SMARKET")
    private EntityManager entityManager;

//    private Session session;

//    @Override
//    public void openConnection() {
//        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
//        session = sessionFactory.openSession();
//    }

    @Override
    public UsersEntity saveUserProfile(UsersEntity userProfile) {
//        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
//        Transaction tx1 = session.beginTransaction();
//        session.save(userProfile);
//        tx1.commit();
//        session.close();
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(userProfile);

        session.getTransaction().commit();
        session.close();


        HibernateUtils.shutdown();
        return userProfile;
    }

    public UsersEntity findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(UsersEntity.class, id);
    }

    @Override
    public String ch() {
        entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        // Check database version
        String sql = "select version()";

        String result = (String) entityManager.createNativeQuery(sql).getSingleResult();
        System.out.println(result);

        entityManager.getTransaction().commit();
        entityManager.close();

        JPAUtil.shutdown();
        return result;
    }

    public void update(UsersEntity user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(user);
        tx1.commit();
        session.close();
    }

    public void delete(UsersEntity user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(user);
        tx1.commit();
        session.close();
    }


    public List<UsersEntity> findAll() {
        List<UsersEntity> users = (List<UsersEntity>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From UsersEntity").list();
        return users;
    }
}
