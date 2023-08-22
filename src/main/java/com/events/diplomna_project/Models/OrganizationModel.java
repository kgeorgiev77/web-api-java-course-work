package com.events.diplomna_project.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "organisation")
public class OrganizationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String email;
    private String name;
    private String password;
    private boolean is_proved;

    public OrganizationModel(Long id, String description, String email, String name, String password, boolean is_proved, UserModel user_id, List<EventModel> events) {
        this.id = id;
        this.description = description;
        this.email = email;
        this.name = name;
        this.password = password;
        this.is_proved = is_proved;
        this.user_id = user_id;
        this.events = events;
    }

    @ManyToOne
    private UserModel user_id;

    @OneToMany(mappedBy = "organisation_id")
    @JsonIgnore
    private List<EventModel> events;

    public OrganizationModel() {
    }

    public OrganizationModel(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public boolean isIs_proved() {
        return is_proved;
    }

    public void setIs_proved(boolean is_proved) {
        this.is_proved = is_proved;
    }

    public List<EventModel> getEvents() {
        return events;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserModel getUser_id() {
        return user_id;
    }

    public void setUser_id(UserModel user_id) {
        this.user_id = user_id;
    }
}
