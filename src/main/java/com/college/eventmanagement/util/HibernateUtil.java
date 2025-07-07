//package com.college.eventmanagement.util;
//
//public class HibernateUtil {
//    private static SessionFactory sessionFactory;
//
//    static {
//        try {
//            sessionFactory = new Configuration().configure().buildSessionFactory();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static SessionFactory getSessionFactory() {
//        return sessionFactory;
//    }
//}

// src/main/java/com/college/eventmanagement/util/HibernateUtil.java
package com.college.eventmanagement.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    static {
        try {
            System.out.println("Initializing Hibernate...");
            sessionFactory = new Configuration().configure().buildSessionFactory();
            System.out.println("✅ Hibernate SessionFactory created successfully!");
        } catch (Exception e) {
            System.err.println("❌ Failed to create SessionFactory: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    // Test method
    public static void testConnection() {
        try {
            SessionFactory factory = getSessionFactory();
            if (factory != null) {
                System.out.println("✅ Database connection successful!");
                System.out.println("Database URL: " + factory.getProperties().get("hibernate.connection.url"));
            }
        } catch (Exception e) {
            System.err.println("❌ Database connection failed: " + e.getMessage());
        }
    }
}