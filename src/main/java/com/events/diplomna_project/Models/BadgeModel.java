package com.events.diplomna_project.Models;



import jakarta.persistence.*;

@Entity
@Table(name = "badges")
public class BadgeModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @ManyToOne
    private UserModel user_id;

    @ManyToOne
    private EventModel event_id;

    public BadgeModel() {
    }

    public BadgeModel(Long id, String message, UserModel user_id, EventModel event_id) {
        this.id = id;
        this.message = message;
        this.user_id = user_id;
        this.event_id = event_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserModel getUser_id() {
        return user_id;
    }

    public void setUser_id(UserModel user_id) {
        this.user_id = user_id;
    }

    public EventModel getEvent_id() {
        return event_id;
    }

    public void setEvent_id(EventModel event_id) {
        this.event_id = event_id;
    }
}
