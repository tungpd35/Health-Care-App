package com.example.healthapp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
public class Request {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Getter
    private Date date;
    @Getter
    private Status status;
    @Getter
    @ManyToOne
    private Customer customer;
    public Request(Date date, Status status, Customer customer, Post post) {
        this.date = date;
        this.status = status;
        this.customer = customer;
        this.post = post;
    }

    @Getter
    @ManyToOne
    @JsonBackReference
    private Post post;

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
