package com.college.eventmanagement.dao;

// Add these imports:
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.college.eventmanagement.util.HibernateUtil;
import com.college.eventmanagement.entities.Event;
import java.util.List;

public class EventDAO implements GenericDAO<Event> {

    @Override
    public void save(Event event) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(event);
        tx.commit();
        session.close();
    }

    @Override
    public Event findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Event event = session.get(Event.class, id);
        session.close();
        return event;
    }

    @Override
    public List<Event> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Event> events = session.createQuery("FROM Event", Event.class).list();
        session.close();
        return events;
    }

    @Override
    public void update(Event event) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(event);
        tx.commit();
        session.close();
    }

    @Override
    public void delete(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Event event = session.get(Event.class, id);
        if (event != null) {
            session.delete(event);
        }
        tx.commit();
        session.close();
    }
}