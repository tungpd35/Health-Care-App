package com.example.healthapp.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Getter
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class , property = "id")
public class HealthStaff extends User{
    private String Biography;
    @ManyToMany
    private List<Service> services;
    @OneToMany(cascade = CascadeType.ALL)

    private List<Post> posts;
    @OneToMany
    private List<Notification> notification;

    public List<Notification> getNotification() {
        return notification;
    }

    public void setNotification(List<Notification> notification) {
        this.notification = notification;
    }

    public HealthStaff(String email, String password) {
        super(email,password,Role.STAFF);
    }

    public HealthStaff(String email, String password, String fullName, Date birth, Address address, String phone, Role role, ImageData avatar) {
        super(email,password,fullName,birth,address,phone,role,avatar);
    }
    public HealthStaff(String fullName,String email, String password, Role role) {
        super(fullName, email, password, role);
    }
    public HealthStaff() {
        super();
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void setBiography(String biography) {
        Biography = biography;
    }

    public void setService(List<Service> service) {
        this.services = service;
    }

}
