package com.company.sailorsmarketplace.dao;

import com.company.sailorsmarketplace.dbmodel.Event;
import com.company.sailorsmarketplace.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;

public class EventDAO {

    public Event save(Event event) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(event);
            tx.commit();

            return event;
        }
    }

    public Event getById(Long id) {
        Event event;

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            event = session.get(Event.class, id);
            return event;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error in getById EventDAO method", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public void update(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Event is null");
        }

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(event);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void delete(Event event) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.delete(event);
            tx.commit();
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }
}
