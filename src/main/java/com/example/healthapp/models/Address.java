package com.example.healthapp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Getter
    private String city;
    @Getter
    private String district;
    @Getter
    private String ward;
    @Getter
    private String detail;
    @Getter
    @OneToOne
    private User user;
    @Getter
    @OneToOne
    private Post post;

    public Address(String city, String district, String ward, String detail, User user, Post post) {
        this.city = city;
        this.district = district;
        this.ward = ward;
        this.detail = detail;
        this.user = user;
        this.post = post;
    }

    public Address(String city, String district, String ward, String detail) {
        this.city = city;
        this.district = district;
        this.ward = ward;
        this.detail = detail;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
