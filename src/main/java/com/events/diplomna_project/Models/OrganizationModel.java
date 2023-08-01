package com.events.diplomna_project.Models;

import jakarta.persistence.*;

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
    @ManyToOne
    private UserModel user_id;

    public OrganizationModel() {
    }

    public OrganizationModel(Long id, String description, String email, String name, String password, UserModel user_id) {
        this.id = id;
        this.description = description;
        this.email = email;
        this.name = name;
        this.password = password;
        this.user_id = user_id;
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
