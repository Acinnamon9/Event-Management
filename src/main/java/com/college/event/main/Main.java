package com.college.event.main;

import com.college.event.dao.EventDao;
import com.college.event.dao.LocationDao;
import com.college.event.dao.ParticipantDao;
import com.college.event.dao.UserDAO;
import com.college.event.entity.Event;
import com.college.event.entity.Location;
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
    private final LocationDao locationDao;

    public Main() {
        this.scanner = new Scanner(System.in);
        this.userDAO = new UserDAO();
        this.eventDao = new EventDao();
        this.participantDao = new ParticipantDao();
        this.locationDao = new LocationDao();
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
                            if ("organizer".equalsIgnoreCase(loggedInUser.getRole())) {
                                showOrganizerDashboard(loggedInUser);
                            } else {
                                showAttendeeDashboard(loggedInUser);
                            }
                        }
                        break;
                    case 2:
                        registerFunction();
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        return;
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
            System.out.println("Login successful. Role: " + user.getRole());
        } else {
            System.out.println("Invalid credentials.");
        }
        return user;
    }

    private void registerFunction() {
        System.out.println("\n--- Attendee Registration ---");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password); // Remember to hash passwords in a real application!
        newUser.setRole("attendee"); // Default role is attendee

        userDAO.saveUser(newUser);
        System.out.println("Registration successful. Welcome, " + name + "!");
    }

    private void showOrganizerDashboard(User organizer) {
        while (true) {
            System.out.println("\n--- ORGANIZER DASHBOARD ---");
            System.out.println("1. Create Event");
            System.out.println("2. View My Events");
            System.out.println("3. View All Events");
            System.out.println("4. Update Event");
            System.out.println("5. Delete Event");
            System.out.println("6. View Registered Attendees for Event");
            System.out.println("7. View All Locations");
            System.out.println("8. Add Location");
            System.out.println("9. Logout");
            int choice = promptForInt("Choose an option: ");

            switch (choice) {
                case 1:
                    createEvent(organizer);
                    break;
                case 2:
                    viewMyEvents(organizer);
                    break;
                case 3:
                    displayAllEvents();
                    break;
                case 4:
                    updateEvent(organizer);
                    break;
                case 5:
                    deleteEvent(organizer);
                    break;
                case 6:
                    viewEventAttendees();
                    break;
                case 7:
                    viewAllLocations();
                    break;
                case 8:
                    addLocation();
                    break;
                case 9:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private void showAttendeeDashboard(User attendee) {
        while (true) {
            System.out.println("\n--- ATTENDEE DASHBOARD ---");
            System.out.println("Welcome, " + attendee.getName() + "!");
            System.out.println("1. View All Events");
            System.out.println("2. Register for Event");
            System.out.println("3. Cancel Registration");
            System.out.println("4. View My Registered Events");
            System.out.println("5. Logout");
            int choice = promptForInt("Choose an option: ");

            switch (choice) {
                case 1:
                    displayAllEvents();
                    break;
                case 2:
                    registerForEvent(attendee);
                    break;
                case 3:
                    cancelRegistration(attendee);
                    break;
                case 4:
                    viewMyRegisteredEvents(attendee);
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    // Placeholder methods for new functionality. You would need to implement these.
    private void createEvent(User organizer) { System.out.println("Functionality not yet implemented."); }
    private void viewMyEvents(User organizer) { System.out.println("Functionality not yet implemented."); }
    private void updateEvent(User organizer) { System.out.println("Functionality not yet implemented."); }
    private void deleteEvent(User organizer) { System.out.println("Functionality not yet implemented."); }
    private void viewEventAttendees() { System.out.println("Functionality not yet implemented."); }
    private void viewAllLocations() { System.out.println("Functionality not yet implemented."); }
    private void addLocation() { System.out.println("Functionality not yet implemented."); }
    private void cancelRegistration(User attendee) { System.out.println("Functionality not yet implemented."); }
    private void viewMyRegisteredEvents(User attendee) { System.out.println("Functionality not yet implemented."); }

    private void registerForEvent(User loggedInUser) {
        displayAllEvents();
        int eventId = promptForInt("Enter the ID of the event you want to register for: ");
        Event eventToRegister = eventDao.getEventById(eventId);

        if (eventToRegister == null) {
            System.out.println("Event with ID " + eventId + " not found.");
            return;
        }

        // You would add checks for event capacity and if user is already registered here.

        Participant newParticipant = new Participant(loggedInUser, eventToRegister);
        if (participantDao.saveParticipant(newParticipant)) {
            System.out.println("Successfully registered for event: " + eventToRegister.getName());
        } else {
            System.out.println("Failed to register for the event.");
        }
    }

    private void displayAllEvents() {
        System.out.println("\n--- All Available Events ---");
        List<Event> events = eventDao.getAllEvents();
        if (events == null || events.isEmpty()) {
            System.out.println("No events found.");
        } else {
            // This would be updated to show new fields like Location, Organizer, etc.
            for (Event event : events) {
                System.out.printf("ID: %d, Name: %s, Description: %s%n",
                        event.getId(), event.getName(), event.getDescription());
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
            return -1; // Indicate failure
        }
    }
}