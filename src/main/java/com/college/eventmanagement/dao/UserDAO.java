package com.college.eventmanagement.dao;

// Add this import:
import com.college.eventmanagement.entities.User;
import com.college.eventmanagement.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class UserDAO implements GenericDAO<User> {

    @Override
    public void save(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(user);
            tx.commit();
            System.out.println("✅ User saved successfully!");
        } catch (Exception e) {
            tx.rollback();
            System.err.println("❌ Error saving user: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public User findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(User.class, id);
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM User", User.class).list();
        } finally {
            session.close();
        }
    }

    @Override
    public void update(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.update(user);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}