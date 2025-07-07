package org.example;

// Add these imports:
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Session;

public class HibernateTest {
    public static void main(String[] args) {
        try {
            // Create SessionFactory
            SessionFactory sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .buildSessionFactory();

            System.out.println("✅ Hibernate SessionFactory created successfully!");

            // Test opening a session
            Session session = sessionFactory.openSession();
            System.out.println("✅ Database session opened successfully!");

            // Close session
            session.close();
            sessionFactory.close();

            System.out.println("✅ Hibernate test completed successfully!");

        } catch (Exception e) {
            System.err.println("❌ Hibernate test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}