package com.college.eventmanagement.entities;

import javax.persistence.*;

@Entity
@Table(name = "registrations")
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long registrationId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Enumerated(EnumType.STRING)
    private RegistrationStatus status;

    // Constructors
    public Registration() {}

    public Registration(User user, Event event, RegistrationStatus status) {
        this.user = user;
        this.event = event;
        this.status = status;
    }

    // Getters and Setters
    public Long getRegistrationId() { return registrationId; }
    public void setRegistrationId(Long registrationId) { this.registrationId = registrationId; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }

    public RegistrationStatus getStatus() { return status; }
    public void setStatus(RegistrationStatus status) { this.status = status; }
}

// Add this enum at the bottom of the file
enum RegistrationStatus {
    PENDING, CONFIRMED, CANCELLED
}