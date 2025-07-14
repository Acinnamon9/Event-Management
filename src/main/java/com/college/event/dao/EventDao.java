package com.college.event.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.college.event.entity.Event;
import com.college.event.entity.Participant;
import com.college.event.util.HibernateUtil;
import java.util.List;

public class EventDao {

    public void saveEvent(Event event) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(event);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void addParticipantToEvent(int eventId, Participant participant) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Get the event from the database
            Event event = session.get(Event.class, eventId);

            if (event != null) {
                // Set the event for the participant
                participant.setEvent(event);
                // Save the participant (which is now linked to the event)
                session.save(participant);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Event getEventWithParticipants(int eventId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Using HQL (Hibernate Query Language) to fetch the event and its participants
            // 'JOIN FETCH' prevents the LazyInitializationException
            Event event = session.createQuery(
                "SELECT e FROM Event e JOIN FETCH e.participants WHERE e.id = :eventId", Event.class)
                .setParameter("eventId", eventId)
                .uniqueResult();
            return event;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Event> getAllEvents() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL (Hibernate Query Language) to retrieve all Event objects
            Query<Event> query = session.createQuery("FROM Event", Event.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Event getEventById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Event.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}