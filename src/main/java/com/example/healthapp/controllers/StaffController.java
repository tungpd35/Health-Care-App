package com.example.healthapp.controllers;

import com.example.healthapp.dto.ProfileDto;
import com.example.healthapp.models.Address;
import com.example.healthapp.models.HealthStaff;
import com.example.healthapp.models.ImageData;
import com.example.healthapp.models.Notification;
import com.example.healthapp.repositories.AddressRepository;
import com.example.healthapp.repositories.HealthStaffRepository;
import com.example.healthapp.repositories.NotificationRepository;
import com.example.healthapp.security.JwtAuthenticationFilter;
import com.example.healthapp.security.JwtTokenProvider;
import com.example.healthapp.services.StorageService;
import com.example.healthapp.utils.ImageUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping
public class StaffController {
    @Autowired
    private StorageService service;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private HealthStaffRepository healthStaffRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @GetMapping("/staff/profile")
    private String profile(Model model, HttpServletRequest request) {
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        String email = jwtTokenProvider.getUsernameFromToken(token);
        HealthStaff healthStaff = healthStaffRepository.findHealthStaffByEmail(email);
        model.addAttribute("staff", healthStaff);
        if(healthStaff.getAddress()== null) {
            System.out.println("oke");
        }
        return "profile";
    }
    @PostMapping("/api/staff/image")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        String uploadImage = String.valueOf(service.uploadImage(file));
        System.out.println(uploadImage);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }
    @GetMapping("/api/staff/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName){
        byte[] imageData=service.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }
    @PostMapping("api/staff/updateInfo")
    public @ResponseBody void updateInfo(@RequestBody ProfileDto profile, HttpServletRequest request) throws IOException {
        System.out.println("oke");
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        String email = jwtTokenProvider.getUsernameFromToken(token);
        HealthStaff healthStaff = healthStaffRepository.findHealthStaffByEmail(email);
        healthStaff.setFullName(profile.getName());
        healthStaff.setBirth(profile.getDate());
        healthStaff.setGender(profile.getGender());
        if(healthStaff.getAddress() == null) {
            Address address = new Address(profile.getAddress().getCity(),profile.getAddress().getDistrict(),profile.getAddress().getWard(),profile.getAddress().getDetail());
            address.setUser(healthStaff);
            addressRepository.save(address);
            healthStaff.setAddress(address);
        } else {
            healthStaff.getAddress().setCity(profile.getAddress().getCity());
            healthStaff.getAddress().setDistrict(profile.getAddress().getDistrict());
            healthStaff.getAddress().setWard(profile.getAddress().getWard());
            healthStaff.getAddress().setDetail(profile.getAddress().getDetail());
        }
        healthStaff.setPhone(profile.getPhoneNumber());
        healthStaff.setBiography(profile.getDescription());
        healthStaffRepository.save(healthStaff);
    }
    @PostMapping("api/staff/updateAvatar")
    public @ResponseBody void updateAvatar(@RequestParam("image") MultipartFile file, HttpServletRequest request) throws IOException {
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        String email = jwtTokenProvider.getUsernameFromToken(token);
        HealthStaff healthStaff = healthStaffRepository.findHealthStaffByEmail(email);
        if(healthStaff.getAvatar() != null) {
            ImageData newImg = ImageData.builder()
                    .name(UUID.randomUUID().toString() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')))
                    .type(file.getContentType())
                    .imageData(ImageUtils.compressImage(file.getBytes())).build();
            healthStaff.getAvatar().setType(newImg.getType());
            healthStaff.getAvatar().setName(newImg.getName());
            healthStaff.getAvatar().setImageData(newImg.getImageData());
        } else {
            ImageData imageData = service.uploadImage(file);
            imageData.setUser(healthStaff);
            healthStaff.setAvatar(imageData);
        }
        healthStaffRepository.save(healthStaff);
    }
    @GetMapping("avatars/{name}")
    public ResponseEntity<?> getAvatar(@PathVariable String name){
        byte[] imageData=service.downloadImage(name);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }
    @GetMapping("/api/staff/readNotice")
    public @ResponseBody void readNotice(HttpServletRequest request) {
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        String email = jwtTokenProvider.getUsernameFromToken(token);
        HealthStaff healthStaff = healthStaffRepository.findHealthStaffByEmail(email);
        List<Notification> notifications = healthStaff.getNotification();
        for (Notification notice : notifications) {
            notice.setSeen(true);
        }
        healthStaff.setNotification(notifications);
        healthStaffRepository.save(healthStaff);
    }
}