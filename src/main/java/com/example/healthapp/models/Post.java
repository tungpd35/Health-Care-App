package com.example.healthapp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @Column(length = 1000)
    private String description;
    @Column(length = 1000)
    private String skills;
    @Column(length = 1000)
    private String experience;
    private Date date;
    private String email;
    private String phoneNumber;
    @OneToOne(cascade = CascadeType.ALL)
    private Address location;
    @ManyToOne
    @JsonBackReference
    private Service service;
    @Getter
    @ManyToOne
    private HealthStaff healthStaff;
    @Getter
    private int totalRequest=0;

    public void setTotalRequest(int totalRequest) {
        this.totalRequest = totalRequest;
    }

    @Getter
    @OneToMany(cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Request> request;
    public Post(String title, String description, String skills, String experience, Date date, String email, String phoneNumber, Address location, Service service, HealthStaff healthStaff) {
        this.title = title;
        this.description = description;
        this.skills = skills;
        this.experience = experience;
        this.date = date;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.service = service;
        this.healthStaff = healthStaff;
    }

    public Post(String description, String skills, String experience, Date date, Address location, Service service, HealthStaff healthStaff) {
        this.description = description;
        this.skills = skills;
        this.experience = experience;
        this.date = date;
        this.location = location;
        this.service = service;
        this.healthStaff = healthStaff;
    }

    public Post() {}
    public void setRequest(List<Request> request) {
        this.request = request;
        this.totalRequest = request.size();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void setLocation(Address location) {
        this.location = location;
    }
}
