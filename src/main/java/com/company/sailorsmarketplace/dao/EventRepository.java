package com.company.sailorsmarketplace.dao;

import com.company.sailorsmarketplace.dbmodel.Event;
import com.company.sailorsmarketplace.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;

public class EventRepository {

    public Event save(Event event) {
        try (final Session session = HibernateUtils.getSessionFactory().openSession()) {
            final Transaction transaction = session.beginTransaction();
            session.save(event);
            transaction.commit();
            return event;
        }
    }

    public Optional<Event> getById(final Long id) {
        try (final Session session = HibernateUtils.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(Event.class, id));
        }
    }

    public void update(Event event) {
        try (final Session session = HibernateUtils.getSessionFactory().openSession()) {
            final Transaction transaction = session.beginTransaction();
            session.update(event);
            transaction.commit();
        }
    }

    public void delete(Event event) {
        try (final Session session = HibernateUtils.getSessionFactory().openSession()) {
            final Transaction transaction = session.beginTransaction();
            session.delete(event);
            transaction.commit();
        }
    }
}
