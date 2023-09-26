package com.example.healthapp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;

@Entity
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    public void setName(String name) {
        this.name = name;
    }
    @ManyToMany(mappedBy = "services")
    private List<HealthStaff> healthStaffs;

    public String getName() {
        return name;
    }

    public List<HealthStaff> getHealthStaffs() {
        return healthStaffs;
    }

    public void setHealthStaffs(List<HealthStaff> healthStaffs) {
        this.healthStaffs = healthStaffs;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @OneToMany
    private List<Post> posts;
}
