package com.events.diplomna_project.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int age;
    private String description;
    private String email;
    private boolean is_admin;
    private String name;
    private String password;
    private String phone;
    private String role;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<BadgeModel> badges;

    public UserModel() {
    }

    public UserModel(Long id, int age, String description, String email, boolean is_admin, String name, String password, String phone, String role, List<BadgeModel> b) {
        this.id = id;
        this.age = age;
        this.description = description;
        this.email = email;
        this.is_admin = is_admin;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.badges = b;
    }

    public List<BadgeModel> getBadges() {
        return badges;
    }

    public void setBadges(List<BadgeModel> badges) {
        this.badges = badges;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isIs_admin() {
        return is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
