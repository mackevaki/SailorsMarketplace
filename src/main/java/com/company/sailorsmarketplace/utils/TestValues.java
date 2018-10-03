package com.company.sailorsmarketplace.utils;

import com.company.sailorsmarketplace.dbmodel.Authority;
import com.company.sailorsmarketplace.dbmodel.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;

public class TestValues {

    private final static User testUser = new User(
            "testUser",
            "#testPass1",
            "test@test.com",
            "+79876543210",
            Authority.ROLE_USER
    );

    public final static User anonymousUser = new User(
            "anonymousGuest",
            "#guestPass1",
            "guest@guest.com",
            "+79876543210",
            Authority.ROLE_ANONYMOUS
    );

    private final static User administrator = new User(
            "admin",
            "#Admin1337",
            "admin@admin.com",
            "+99999999999",
            Authority.ROLE_ADMIN
    );

    public static void setValues() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM User WHERE id IS NOT NULL");
            session.save(administrator);
            session.save(testUser);
            tx.commit();
        }
    }

}
