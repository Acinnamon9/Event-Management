package com.college.event.dao;

import com.college.event.entity.Location;
import com.college.event.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class LocationDao {

    /**
     * Saves a new Location to the database.
     * @param location The Location object to save.
     */
    public void saveLocation(Location location) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(location);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.err.println("Error saving location: " + e.getMessage());
        }
    }

    /**
     * Retrieves a Location by its ID.
     * @param id The ID of the location to retrieve.
     * @return The Location object if found, otherwise null.
     */
    public Location getLocationById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Location.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves all locations from the database.
     * @return A list of all Location objects.
     */
    public List<Location> getAllLocations() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Location> query = session.createQuery("FROM Location", Location.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}