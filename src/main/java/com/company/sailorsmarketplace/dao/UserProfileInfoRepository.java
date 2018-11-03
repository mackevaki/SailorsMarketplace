package com.company.sailorsmarketplace.dao;

import com.company.sailorsmarketplace.dbmodel.UserProfileInfo;
import com.company.sailorsmarketplace.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import java.util.Optional;

public class UserProfileInfoRepository {

    public UserProfileInfo save(final UserProfileInfo profileInfo) {
        try (final Session session = HibernateUtils.getSessionFactory().openSession()) {
            final Transaction tx = session.beginTransaction();
            session.save(profileInfo);
            tx.commit();
            return profileInfo;
        }
    }

    public Optional<UserProfileInfo> getById(final Long id) {
        try (final Session session = HibernateUtils.getSessionFactory().openSession()) {
            return Optional.of(session.get(UserProfileInfo.class, id));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public void update(final UserProfileInfo profileInfo) {
        try (final Session session = HibernateUtils.getSessionFactory().openSession()) {
            final Transaction transaction = session.beginTransaction();
            session.update(profileInfo);
            transaction.commit();
        }
    }

    public void delete(final UserProfileInfo profileInfo) {
        try (final Session session = HibernateUtils.getSessionFactory().openSession()) {
            final Transaction transaction = session.beginTransaction();
            session.delete(profileInfo);
            transaction.commit();
        }
    }
}