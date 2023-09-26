package com.example.healthapp.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@Entity
public class Customer extends User {
    @OneToMany(cascade = CascadeType.ALL)
    private List<Request> request;
    public Customer(String email, String password, String fullName, Date birth, Address address, String phone, Role role,ImageData avatar) {
        super(email,password,fullName,birth,address,phone,role,avatar);
    }
    public Customer(String fullName, String phoneNumber,String email, String password, Role role) {
        super(fullName, phoneNumber, email, password, role);
    }

    public void setRequest(List<Request> request) {
        this.request = request;
    }

    public Customer() {
        super();
    }
}
