package com.events.diplomna_project.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "events")

public class EventModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;
    private String description;
    private String donation;
    private String goal;
    private String name;
    private boolean is_confirmed_by_admin;

    @ManyToOne
    @JoinColumn(name = "organisation_id_id")
    private  OrganizationModel organisation_id;

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    private List<BadgeModel> badges;

    @ManyToMany
    private List<UserModel> volunteers = new ArrayList<>();

    public List<BadgeModel> getBadges() {
        return badges;
    }

    public void setBadges(List<BadgeModel> badges) {
        this.badges = badges;
    }

    public EventModel() {
    }

    public EventModel(Long id, Date date, String description, String donation, String goal, String name, boolean is_confirmed_by_admin, OrganizationModel organisation_id, List<BadgeModel> b) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.donation = donation;
        this.goal = goal;
        this.name = name;
        this.is_confirmed_by_admin = is_confirmed_by_admin;
        this.organisation_id = organisation_id;
        this.badges = b;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public List<UserModel> getVolunteers() {
        return volunteers;
    }

    public void setVolunteers(List<UserModel> volunteers) {
        this.volunteers = volunteers;
    }
}
