package com.college.eventmanagement.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date eventDate;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;

    // Default constructor (required by Hibernate)
    public Event() {}

    // Parameterized constructor
    public Event(String title, String description, Date eventDate, User organizer) {
        this.title = title;
        this.description = description;
        this.eventDate = eventDate;
        this.organizer = organizer;
    }

    // Getters and Setters
    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Date getEventDate() { return eventDate; }
    public void setEventDate(Date eventDate) { this.eventDate = eventDate; }

    public User getOrganizer() { return organizer; }
    public void setOrganizer(User organizer) { this.organizer = organizer; }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", eventDate=" + eventDate +
                ", organizer=" + organizer +
                '}';
    }
}