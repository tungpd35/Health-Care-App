package com.example.healthapp.controllers;

import com.example.healthapp.models.CustomUserDetails;
import com.example.healthapp.models.Service;
import com.example.healthapp.payload.Request;
import com.example.healthapp.payload.Response;
import com.example.healthapp.repositories.ServiceRepository;
import com.example.healthapp.security.JwtTokenProvider;
import com.example.healthapp.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class HomeController {
    @Autowired
    private ServiceRepository serviceRepository;
    @GetMapping("/")
    public String homePage1(Model model) {
        List<Service> services = serviceRepository.findAll();
        System.out.println(services);
        model.addAttribute("service", services);
        return "home";
    }
    @GetMapping("/home")
    public String homePage(Model model) {
        List<Service> services = serviceRepository.findAll();
        System.out.println(services);
        model.addAttribute("service", services);
        return "home";

    }
    @GetMapping("/staff/home")
    public @ResponseBody String homeStaffPage() {
        return "homeStaff";
    }
}
