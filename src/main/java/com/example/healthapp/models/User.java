package com.example.healthapp.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
@Getter
@Entity
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String password;
    private String fullName;
    private Gender gender;
    private Date birth;

    @OneToOne
    private Address address;
    private String phone;
    private Role role;
    @OneToOne
    private ImageData avatar;
    @OneToOne
    private PasswordResetToken passwordResetToken;


    public void setPasswordResetToken(PasswordResetToken passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

    public User(String email, String password, String fullName, Date birth, Address address, String phone, Role role,ImageData avatar) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.birth = birth;
        this.address = address;
        this.phone = phone;
        this.role = role;
        this.avatar = avatar;
    }

    public User(String fullName,String email, String password, Role role ) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
    }

    public User(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
    public User(String fullName,String phoneNumber, String email, String password, Role role) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.phone = phoneNumber;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setAvatar(ImageData avatar) {
        this.avatar = avatar;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
