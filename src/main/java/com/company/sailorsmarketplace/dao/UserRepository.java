package com.company.sailorsmarketplace.dao;

import com.company.sailorsmarketplace.dbmodel.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import static com.company.sailorsmarketplace.utils.HibernateUtils.getSessionFactory;

public class UserRepository {

    public User save(final User user) {
        try (final Session session = getSessionFactory().openSession()) {
            final Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            return user;
        }
    }

    public Optional<User> getById(final Long id) {
        try (final Session session = getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(User.class, id));
        }
    }

    public Optional<User> getByUsername(final String username) {
        try(final Session session = getSessionFactory().openSession()) {
            TypedQuery<User> query = session.createQuery("from User where username = :username", User.class);
            query.setParameter("username", username);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public Optional<User> getByEmail(final String findEmail) {
        try (final Session session = getSessionFactory().openSession()) {
            Query<User> q = session.createNativeQuery(
                    "SELECT e.* FROM smarket.users e WHERE e.email = :findEmail", User.class);
            q.setHint("eclipselink.cache-usage", "DoNotCheckCache");
            q.setParameter("findEmail", findEmail);
            return Optional.of(q.getSingleResult());
        } catch (NoResultException ne) {
            return Optional.empty();
        }
    }

    public User update(User user) {
        try (final Session session = getSessionFactory().openSession()) {
            final Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
            return user;
        }
    }

    public void delete(User user) {
        try (final Session session = getSessionFactory().openSession()) {
            final Transaction transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
        }
    }

    public List<User> findAll() {
        try (final Session session = getSessionFactory().openSession()) {
            return session.createQuery("select u from User u", User.class).getResultList();
        }
    }
}
