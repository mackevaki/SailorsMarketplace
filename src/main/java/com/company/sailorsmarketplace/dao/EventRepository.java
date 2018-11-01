package com.company.sailorsmarketplace.dao;

import com.company.sailorsmarketplace.dbmodel.Event;
import com.company.sailorsmarketplace.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;

public class EventRepository {

    public Event save(Event event) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(event);
            tx.commit();

            return event;
        }
    }

    public Optional<Event> getById(Long id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(Event.class, id));
        }
    }

    public void update(Event event) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(event);
            transaction.commit();
        }
    }

    public void delete(Event event) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(event);
            transaction.commit();
        }
    }
}
