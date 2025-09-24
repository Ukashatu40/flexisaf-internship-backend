package com.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {

        //System.out.println("Hello world!");

        Alien a1 = new Alien();
        a1.setAid(101);
        a1.setAname("Ukashat");
        a1.setTech("Software Engineer");

        //Configuration config = new Configuration();
        //config.addAnnotatedClass(com.example.Alien.class);
        //config.configure();

        SessionFactory factory = new Configuration()
                .addAnnotatedClass(com.example.Alien.class)
                .configure()
                .buildSessionFactory();

        Session session = factory.openSession();

        Transaction transaction = session.beginTransaction();

        // current
        //Alien a1 = session.find(Alien.class, 101);

        // Deprecated
        //Alien a2 = session.get(Alien.class, 101);

        //new
        //Alien a3 = session.byId(Alien.class).load(101);

        //Alien a3 = session.byId(Alien.class).getReference(101); // lazy fetching

        session.persist(a1);

        transaction.commit();

        //System.out.println(a3);

        session.close();
        factory.close();
    }
}