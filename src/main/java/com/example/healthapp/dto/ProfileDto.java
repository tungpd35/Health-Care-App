package com.example.healthapp.dto;

import com.example.healthapp.models.Address;
import com.example.healthapp.models.Gender;
import com.example.healthapp.models.ImageData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ProfileDto {
    private String name;
    private Gender gender;
    private Date date;
    private Address address;
    private String phoneNumber;
    private String description;
}
