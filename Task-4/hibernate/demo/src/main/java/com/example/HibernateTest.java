package com.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.time.LocalDate;

public class HibernateTest {

    public static void main(String[] args) {

        // 1. Load the Hibernate Configuration and build the SessionFactory
        // Hibernate automatically looks for hibernate.cfg.xml in the root classpath
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml") // Loads your DB settings

                // 🚨 THIS IS THE CRITICAL MISSING STEP 🚨
                .addAnnotatedClass(PlatformUser.class)
                .addAnnotatedClass(Cohort.class)     // Don't forget related entities!
                .addAnnotatedClass(Course.class)     // Don't forget related entities!

                .buildSessionFactory();


        Session session = null;

        // --- 1. PERSIST (SAVE) OPERATION ---
        try {
            session = factory.openSession();
            session.beginTransaction();

            // Create embedded object first (assuming LocationDetails is in com.flexisaf)
            LocationDetails userAddress = new LocationDetails();
            userAddress.setCity("Kaduna");
            userAddress.setCountry("Nigeria");

            // Create the entity instance
            PlatformUser newUser = new PlatformUser();
            newUser.setFirstName("Usman");
            // NOTE: Ensure your port (5433) is correct and the 'ukasha' database exists.
            newUser.setEmail("usman.test@flexisaf.com");
            newUser.setPasswordHash("usman_secure_hash");
            newUser.setDateOfBirth(LocalDate.of(1995, 8, 20));
            newUser.setRole(UserRole.TEACHER);
            newUser.setNotes("PostgreSQL test user.");
            newUser.setActive(true);
            newUser.setAddress(userAddress);

            // 2. Save the entity
            session.persist(newUser);

            // Commit transaction to execute SQL and save to PostgreSQL
            session.getTransaction().commit();

            System.out.println("✅ User persisted successfully with ID: " + newUser.getId());

            // --- 2. FIND (RETRIEVE) OPERATION ---
            Long persistedId = newUser.getId();

            // Start a new transaction/session for retrieval (good practice)
            session.close();
            session = factory.openSession();
            session.beginTransaction();

            // Retrieve the user by ID
            PlatformUser retrievedUser = session.find(PlatformUser.class, persistedId);

            if (retrievedUser != null) {
                System.out.println("\n🔍 Retrieved from PostgreSQL:");
                System.out.println("Full Name: " + retrievedUser.getFirstName() + " (Role: " + retrievedUser.getRole() + ")");
                System.out.println("Location: " + retrievedUser.getAddress().getCity() + ", " + retrievedUser.getAddress().getCountry());
            }

        } catch (Exception e) {
            // Handle rollback on error
            if (session != null && session.getTransaction() != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            System.err.println("An error occurred during Hibernate operation:");
            e.printStackTrace();
        } finally {
            // Close resources
            if (session != null) {
                session.close();
            }
            factory.close();
        }
    }
}