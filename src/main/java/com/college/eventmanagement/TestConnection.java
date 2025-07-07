package com.college.eventmanagement;

import com.college.eventmanagement.util.HibernateUtil;

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("Testing Hibernate Configuration...");

        // This will trigger the static block in HibernateUtil
        HibernateUtil.testConnection();

        // Clean shutdown
        if (HibernateUtil.getSessionFactory() != null) {
            HibernateUtil.getSessionFactory().close();
            System.out.println("✅ SessionFactory closed successfully!");
        }
    }
}