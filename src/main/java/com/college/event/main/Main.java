package com.college.event.main;

import com.college.event.dao.EventDao;
import com.college.event.dao.UserDAO;
import com.college.event.entity.Event;
import com.college.event.entity.Participant;
import com.college.event.entity.User;
import com.college.event.util.HibernateUtil;

import java.util.Scanner;

public class Main {

    public static void loginFunction(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        UserDAO userDAO = new UserDAO();
        User user = userDAO.authenticate(email, password);

        if (user != null) {
            System.out.println("Login successful as " + user.getRole());
            // redirect based on role
        } else {
            System.out.println("Invalid credentials.");
        }

    }


    public static void main(String[] args) {
        EventDao eventDao = new EventDao();
        UserDAO userDAO = new UserDAO(); // Instantiate UserDAO

        // Create and save a test user (for initial setup/testing)
        // You should implement a proper registration flow for a real application
        User testUser = new User();
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");
        testUser.setPassword("password123"); // IMPORTANT: Hash passwords in real applications!
        testUser.setRole("admin");
        userDAO.saveUser(testUser);
        System.out.println("Test user 'test@example.com' saved.");


        loginFunction();

        // 1. Create and Save a new Event
        System.out.println("--- Creating and Saving a New Event ---");
        Event codeFest = new Event("CodeFest 2024", "Annual coding competition", "2024-10-26", "Main Auditorium");
        eventDao.saveEvent(codeFest);
        System.out.println("Event 'CodeFest 2024' saved with ID: " + codeFest.getId());
        System.out.println("----------------------------------------\n");


        // 2. Add Participants to the Event
        System.out.println("--- Adding Participants to CodeFest ---");
        Participant alice = new Participant("Alice", "alice@example.com");
        Participant bob = new Participant("Bob", "bob@example.com");

        // We use the ID that Hibernate assigned to our event object
        eventDao.addParticipantToEvent(codeFest.getId(), alice);
        eventDao.addParticipantToEvent(codeFest.getId(), bob);
        System.out.println("Added Alice and Bob to the event.");
        System.out.println("----------------------------------------\n");


        // 3. Retrieve the Event and its Participants to verify
        System.out.println("--- Retrieving Event and Participants ---");
        Event retrievedEvent = eventDao.getEventWithParticipants(codeFest.getId());

        if (retrievedEvent != null) {
            System.out.println("Retrieved Event: " + retrievedEvent.getName());
            System.out.println("Venue: " + retrievedEvent.getVenue());
            System.out.println("Participants Registered:");
            for (Participant p : retrievedEvent.getParticipants()) {
                System.out.println("  - " + p.getName() + " (" + p.getEmail() + ")");
            }
        } else {
            System.out.println("Could not find an event with ID: " + codeFest.getId());
        }
        System.out.println("----------------------------------------\n");

        // 4. Shutdown the Hibernate SessionFactory
        HibernateUtil.shutdown();
        System.out.println("--- Application Finished ---");
    }
}