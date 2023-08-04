package com.events.diplomna_project.Models;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name = "badges")
public class BadgeModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private EventModel event;

    public BadgeModel() {
    }

    public BadgeModel(Long id, String message, UserModel user_id, EventModel event_id) {
        this.id = id;
        this.message = message;
        this.user = user_id;
        this.event = event_id;
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
        return user;
    }

    public void setUser_id(UserModel user_id) {
        this.user = user_id;
    }

    public EventModel getEvent_id() {
        return event;
    }

    public void setEvent_id(EventModel event_id) {
        this.event = event_id;
    }
}
