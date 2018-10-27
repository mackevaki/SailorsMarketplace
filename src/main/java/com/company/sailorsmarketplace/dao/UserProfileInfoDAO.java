package com.company.sailorsmarketplace.dao;

import com.company.sailorsmarketplace.dbmodel.UserProfileInfo;
import com.company.sailorsmarketplace.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserProfileInfoDAO implements DAO<UserProfileInfo>{

    @Override
    public UserProfileInfo save(UserProfileInfo profileInfo) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(profileInfo);
            tx.commit();
            return profileInfo;
        }
    }

    @Override
    public UserProfileInfo getById(Long id) {
        UserProfileInfo userProfileInfo;

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            userProfileInfo = session.get(UserProfileInfo.class, id);
            return userProfileInfo;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void update(UserProfileInfo profileInfo) {
        if (profileInfo == null) {
            throw new IllegalArgumentException("UserProfileInfo is null");
        }

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(profileInfo);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(UserProfileInfo profileInfo) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.delete(profileInfo);
            tx.commit();
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

}
