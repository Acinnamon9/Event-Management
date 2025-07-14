package com.college.event.main;

import com.college.event.dao.EventDao;
import com.college.event.dao.ParticipantDao;
import com.college.event.dao.UserDAO;
import com.college.event.entity.Event;
import com.college.event.entity.Participant;
import com.college.event.entity.User;
import com.college.event.util.HibernateUtil;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final Scanner scanner;
    private final UserDAO userDAO;
    private final EventDao eventDao;
    private final ParticipantDao participantDao;

    public Main() {
        this.scanner = new Scanner(System.in);
        this.userDAO = new UserDAO();
        this.eventDao = new EventDao();
        this.participantDao = new ParticipantDao();
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.run();
    }

    public void run() {
        try {
            while (true) {
                System.out.println("\n--- WELCOME TO EVENT MANAGEMENT ---");
                System.out.println("1. Login");
                System.out.println("2. Register");
                System.out.println("3. Exit");
                int choice = promptForInt("Choose an option: ");

                switch (choice) {
                    case 1:
                        User loggedInUser = loginFunction();
                        if (loggedInUser != null) {
                            if ("admin".equalsIgnoreCase(loggedInUser.getRole())) {
                                showAdminDashboard();
                            } else {
                                showStudentDashboard(loggedInUser);
                            }
                        }
                        break;
                    case 2:
                        registerFunction();
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        return; // Exit the run method
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } finally {
            scanner.close();
            HibernateUtil.shutdown();
        }
    }

    private User loginFunction() {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = userDAO.authenticate(email, password);
        if (user != null) {
            System.out.println("Login successful as " + user.getRole());
        } else {
            System.out.println("Invalid credentials.");
        }
        return user;
    }

    private void registerFunction() {
        System.out.println("\n--- Registration ---");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password); // In a real app, hash the password!
        newUser.setRole("student");

        userDAO.saveUser(newUser); // Assuming this handles its own success/error messages
        System.out.println("Registration successful. Welcome, " + name + "!");
    }

    private void showAdminDashboard() {
        while (true) {
            System.out.println("\n--- Admin Dashboard ---");
            System.out.println("1. Create Event");
            System.out.println("2. View Events");
            System.out.println("3. Logout");
            int choice = promptForInt("Choose an option: ");

            switch (choice) {
                case 1:
                    createEvent();
                    break;
                case 2:
                    displayEvents();
                    break;
                case 3:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void showStudentDashboard(User loggedInUser) {
        while (true) {
            System.out.println("\n--- Student Dashboard ---");
            System.out.println("Welcome, " + loggedInUser.getName() + "!");
            System.out.println("1. View Events");
            System.out.println("2. Register for Event");
            System.out.println("3. Logout");
            int choice = promptForInt("Choose an option: ");

            switch (choice) {
                case 1:
                    displayEvents();
                    break;
                case 2:
                    registerForEvent(loggedInUser);
                    break;
                case 3:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void createEvent() {
        System.out.print("Enter event name: ");
        String name = scanner.nextLine();
        System.out.print("Enter event description: ");
        String description = scanner.nextLine();
        System.out.print("Enter event date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter event venue: ");
        String venue = scanner.nextLine();

        Event newEvent = new Event(name, description, date, venue);
        eventDao.saveEvent(newEvent); // Assuming this handles its own success/error messages
        System.out.println("Event '" + name + "' created successfully.");
    }

    private void registerForEvent(User loggedInUser) {
        displayEvents();
        int eventId = promptForInt("Enter the ID of the event you want to register for: ");
        Event eventToRegister = eventDao.getEventById(eventId);

        if (eventToRegister == null) {
            System.out.println("Event with ID " + eventId + " not found.");
            return;
        }

        if (participantDao.isUserRegistered(loggedInUser, eventToRegister)) {
            System.out.println("You are already registered for this event.");
            return;
        }

        Participant newParticipant = new Participant(loggedInUser, eventToRegister);
        if (participantDao.saveParticipant(newParticipant)) {
            System.out.println("Successfully registered for event: " + eventToRegister.getName());
        } else {
            System.out.println("Failed to register for the event. Please try again later.");
        }
    }

    private void displayEvents() {
        System.out.println("\n--- Available Events ---");
        List<Event> events = eventDao.getAllEvents();
        if (events == null || events.isEmpty()) {
            System.out.println("No events found.");
        } else {
            for (Event event : events) {
                System.out.printf("ID: %d, Name: %s, Description: %s, Date: %s, Venue: %s%n",
                        event.getId(), event.getName(), event.getDescription(), event.getDate(), event.getVenue());
            }
        }
    }

    private int promptForInt(String message) {
        System.out.print(message);
        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            return choice;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // Consume the invalid input
            return -1; // Return a value indicating failure
        }
    }
}