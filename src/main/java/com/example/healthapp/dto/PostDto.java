package com.example.healthapp.dto;

import com.example.healthapp.models.Address;
import com.example.healthapp.models.HealthStaff;
import com.example.healthapp.models.Service;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private String title;
    private String description;
    private String skills;
    private String experience;
    private Date date;
    private String phoneNumber;
    private String email;
    private Address location;
    private Long serviceId;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getSkills() {
        return skills;
    }

    public String getExperience() {
        return experience;
    }

    public Date getDate() {
        return date;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getLocation() {
        return location;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public void setLocation(Address location) {
        this.location = location;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }


}
