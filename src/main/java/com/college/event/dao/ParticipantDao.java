package com.college.event.dao;

import com.college.event.entity.Event;
import com.college.event.entity.Participant;
import com.college.event.entity.User;
import com.college.event.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class ParticipantDao {

    /**
     * Saves a participant to the database.
     * @param participant the participant to save
     * @return true if the participant was saved successfully, false otherwise.
     */
    public boolean saveParticipant(Participant participant) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(participant);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.err.println("Error saving participant: " + e.getMessage());
            return false;
        }
    }

    /**
     * Checks if a user is already registered for a specific event.
     * @param user the user to check
     * @param event the event to check
     * @return true if the user is already registered, false otherwise.
     */
    public boolean isUserRegistered(User user, Event event) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT count(p.id) FROM Participant p WHERE p.user = :user AND p.event = :event", Long.class);
            query.setParameter("user", user);
            query.setParameter("event", event);
            Long count = query.uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            // In case of error, assume not registered to be safe, but log it.
            return false;
        }
    }
}