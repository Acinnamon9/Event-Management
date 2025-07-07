package com.college.eventmanagement;

// Add these imports:
import com.college.eventmanagement.services.UserService;
import com.college.eventmanagement.services.EventService;
import com.college.eventmanagement.entities.User;
import com.college.eventmanagement.entities.Event;
import com.college.eventmanagement.entities.Role;
import java.util.Date;

public class CollegeEventApp {
    public static void main(String[] args) {
        // Create sample data
        UserService userService = new UserService();
        EventService eventService = new EventService();

        // Test your CRUD operations
        User admin = new User("John Doe", "john@college.edu", Role.ADMIN);
        userService.createUser(admin);

        // Create events, register users, etc.
        System.out.println("College Event Management System Started!");

        // Test creating an event
        Event event = new Event("Tech Fest 2024", "Annual technology festival", new Date(), admin);
        eventService.createEvent(event.getTitle(), event.getDescription(), event.getEventDate(), admin);

        System.out.println("Sample data created successfully!");
    }
}