package com.example.healthapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String content;
    private boolean seen;

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public Notification(String title, String content, boolean seen, HealthStaff healthStaff) {
        this.title = title;
        this.content = content;
        this.seen = seen;
        this.healthStaff = healthStaff;
    }

    @ManyToOne
    private HealthStaff healthStaff;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public HealthStaff getHealthStaff() {
        return healthStaff;
    }

    public void setHealthStaff(HealthStaff healthStaff) {
        this.healthStaff = healthStaff;
    }
}
