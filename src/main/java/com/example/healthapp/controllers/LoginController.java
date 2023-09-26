package com.example.healthapp.controllers;

import com.example.healthapp.models.CustomUserDetails;
import com.example.healthapp.payload.Request;
import com.example.healthapp.payload.Response;
import com.example.healthapp.repositories.UserRepository;
import com.example.healthapp.security.JwtTokenProvider;
import com.example.healthapp.security.UserService;
import jakarta.persistence.Entity;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
@CrossOrigin
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    @PostMapping("/login")
    public @ResponseBody Response loginPage(@RequestBody Request request, HttpServletResponse response) {
        System.out.println(request.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword())
        );
        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Trả về jwt cho người dùng.
        String jwt = jwtTokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        Cookie cookie = new Cookie("token",jwt);
        response.addCookie(cookie);
        return new Response(jwt,request.getUsername());
    }
    @GetMapping("/staff/login")
    public String loginStaffPage() {
        return "stafflogin";
    }
    @PostMapping("/staff/login")
    public @ResponseBody Response loginStaffPage(@RequestBody Request request, HttpServletResponse response) {
        System.out.println(request.getPassword()+request.getUsername());
        System.out.println(userRepository.findUserByEmail(request.getUsername()));
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword())
        );
        System.out.println(request.getPassword());
        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Trả về jwt cho người dùng.
        String jwt = jwtTokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        Cookie cookie = new Cookie("token",jwt);
        response.addCookie(cookie);
        return new Response(jwt,request.getUsername());
    }
}
