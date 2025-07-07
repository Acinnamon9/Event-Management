package com.college.eventmanagement.services;

import com.college.eventmanagement.dao.EventDAO;
import com.college.eventmanagement.entities.Event;
import com.college.eventmanagement.entities.User;
import java.util.Date;
import java.util.List;

public class EventService {
    private EventDAO eventDAO = new EventDAO();

    public void createEvent(String title, String description, Date date, User organizer) {
        Event event = new Event(title, description, date, organizer);
        eventDAO.save(event);
        System.out.println("Event created: " + title);
    }

    public void createEvent(Event event) {
        eventDAO.save(event);
        System.out.println("Event created: " + event.getTitle());
    }

    public Event getEventById(Long id) {
        return eventDAO.findById(id);
    }

    public List<Event> getAllEvents() {
        return eventDAO.findAll();
    }

    public void updateEvent(Event event) {
        eventDAO.update(event);
        System.out.println("Event updated: " + event.getTitle());
    }

    public void deleteEvent(Long id) {
        eventDAO.delete(id);
        System.out.println("Event deleted with ID: " + id);
    }
}