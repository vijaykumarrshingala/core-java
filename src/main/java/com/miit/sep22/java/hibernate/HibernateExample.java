package com.miit.sep22.java.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateExample {


    private static SessionFactory sessionFactory = getSessionFactory();

    private static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.configure("hibernate/hibernate.cfg.xml");
                sessionFactory = configuration.buildSessionFactory();
            } catch (Throwable ex) {
                ex.printStackTrace();
                System.err.println("Initial SessionFactory creation failed." + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }

        return sessionFactory;
    }

    public static void main(String[] args) {
        Session session = sessionFactory.openSession();
        System.out.println(session);
    }
}





