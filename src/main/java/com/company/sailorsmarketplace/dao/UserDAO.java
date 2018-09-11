package com.company.sailorsmarketplace.dao;

import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.exceptions.UserNotFoundException;
import com.company.sailorsmarketplace.utils.HibernateUtils;
import org.hibernate.*;

import javax.inject.Singleton;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.swing.*;
import java.util.List;

//@Singleton
public class UserDAO implements Database {
    public UserDAO() {}

    @Override
    public User save(User user) {
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
    public User getById(Long id) {
        User user;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            user = session.get(User.class, id);
            return user;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при getbyid", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public User getByUsername(String username) throws UserNotFoundException{
        User user;
        try(Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("from User where username='" + username + "'");
            user = (User) query.getSingleResult();
            tx.commit();
            session.clear();
            return user;
        } catch (NoResultException ex) {
            throw new UserNotFoundException();
        }
    }

    @Override
    public User getByEmail(String findEmail) throws UserNotFoundException {
        User user;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Query query = session.createQuery("select ue from User ue where email = :findEmail", User.class);
            query.setParameter("findEmail", findEmail);
            user = (User)query.getSingleResult();
            tx.commit();
            session.clear();
            return user;
        } catch (NoResultException ne) {
            throw new UserNotFoundException(findEmail);
        }
    }

    @Override
    public void update(User user) {
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
    public void delete(User user) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.delete(user);
            tx.commit();
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    @Override
    public List<User> findAll() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<User> list = session.createQuery("select DISTINCT ue from User ue", User.class).getResultList();
        tx.commit();
        session.close();
        return list;
    }
}
