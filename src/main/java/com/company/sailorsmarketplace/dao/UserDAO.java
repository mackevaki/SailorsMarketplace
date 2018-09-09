package com.company.sailorsmarketplace.dao;

import com.company.sailorsmarketplace.dbmodel.UsersEntity;
import com.company.sailorsmarketplace.utils.HibernateUtils;
import org.hibernate.*;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.swing.*;
import java.util.List;

//@Singleton
public class UserDAO implements Database {

    @Override
    public UsersEntity save(UsersEntity user) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(user);
            tx.commit();
            return user;
        } catch (ExceptionInInitializerError ex) {
            System.out.println(ex.getException().getMessage());
            return null;
        }
    }

    @Override
    public UsersEntity getById(Long id) {
        UsersEntity user;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            user = session.get(UsersEntity.class, id);
            return user;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при getbyid", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public UsersEntity getByUsername(String username) {
        return null;
    }

    @Override
    public UsersEntity getByEmail(String findEmail) {
        UsersEntity user;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Query query = session.createQuery("select ue from UsersEntity ue where email = :findEmail", UsersEntity.class);
            query.setParameter("findEmail", findEmail);
            user = (UsersEntity)query.getSingleResult();
            tx.commit();
            session.clear();
            return user;
        } catch (NoResultException ne) {
            return null;
        }
    }

    @Override
    public void update(UsersEntity user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        }

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(user);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(UsersEntity user) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.delete(user);
            tx.commit();
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    @Override
    public List<UsersEntity> findAll() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<UsersEntity> list = session.createQuery("select DISTINCT ue from UsersEntity ue", UsersEntity.class).getResultList();
        tx.commit();
        session.close();
        return list;//(List<UsersEntity>) HibernateUtils.getSessionFactory().openSession().createQuery("From UsersEntity").list();
    }
}
