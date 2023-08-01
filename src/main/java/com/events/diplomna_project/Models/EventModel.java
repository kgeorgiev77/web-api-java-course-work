package com.events.diplomna_project.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "events")

public class EventModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int age;
    private String description;
    private String donation;
    private String goal;
    private String name;
    private boolean is_confirmed_by_admin;

    @ManyToOne
    private  OrganizationModel organisation_id;

    public EventModel() {
    }

    public EventModel(Long id, int age, String description, String donation, String goal, String name, boolean is_confirmed_by_admin, OrganizationModel organisation_id) {
        this.id = id;
        this.age = age;
        this.description = description;
        this.donation = donation;
        this.goal = goal;
        this.name = name;
        this.is_confirmed_by_admin = is_confirmed_by_admin;
        this.organisation_id = organisation_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDonation() {
        return donation;
    }

    public void setDonation(String donation) {
        this.donation = donation;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIs_confirmed_by_admin() {
        return is_confirmed_by_admin;
    }

    public void setIs_confirmed_by_admin(boolean is_confirmed_by_admin) {
        this.is_confirmed_by_admin = is_confirmed_by_admin;
    }

    public OrganizationModel getOrganisation_id() {
        return organisation_id;
    }

    public void setOrganisation_id(OrganizationModel organisation_id) {
        this.organisation_id = organisation_id;
    }
}
