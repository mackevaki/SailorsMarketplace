package com.company.sailorsmarketplace.dao;

import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.exceptions.UserNotFoundException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import static com.company.sailorsmarketplace.utils.HibernateUtils.getSessionFactory;

public class UserRepository {

    public User save(User user) {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();

            return user;
        }
    }

    public Optional<User> getById(Long id) {
        try (Session session = getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(User.class, id));
        }
    }

    public Optional<User> getByUsername(String username) throws UserNotFoundException {
        try (Session session = getSessionFactory().openSession()) {
            TypedQuery<User> query = session.createQuery("from User where username = :username", User.class);
            query.setParameter("username", username);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public Optional<User> getByEmail(String findEmail) {
        try (Session session = getSessionFactory().openSession()) {
            TypedQuery<User> query = session.createQuery("from User where email = :findEmail", User.class);
            query.setParameter("findEmail", findEmail);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException ne) {
            return Optional.empty();
        }
    }

    public User update(User user) {
        try (Session session = getSessionFactory().openSession()) {
            final Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();

            return user;
        }
    }

    public void delete(User user) {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
        }
    }

    public List<User> findAll() {
        try (Session session = getSessionFactory().openSession()) {
            return session.createQuery("select u from User u", User.class).getResultList();
        }
    }
}
