package com.example.healthapp.controllers;

import com.example.healthapp.dto.ChangePasswordDto;
import com.example.healthapp.dto.ProfileCustomerDto;
import com.example.healthapp.models.Customer;
import com.example.healthapp.repositories.CustomerRepository;
import com.example.healthapp.security.JwtAuthenticationFilter;
import com.example.healthapp.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class CustomerController {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/profile")
    public String profile(HttpServletRequest request, Model model) {
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        String email = jwtTokenProvider.getUsernameFromToken(token);
        Customer customer = customerRepository.findCustomerByEmail(email);
        model.addAttribute("customer", customer);
        return "profilecustomer";
    }
    @GetMapping("/change-password")
    public String changePassword(HttpServletRequest request, Model model) {
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        String email = jwtTokenProvider.getUsernameFromToken(token);
        Customer customer = customerRepository.findCustomerByEmail(email);
        model.addAttribute("customer", customer);
        return "changepassword";
    }
    @PutMapping("api/customer/updateProfile")
    public @ResponseBody void updateProfile(@RequestBody ProfileCustomerDto profileCustomerDto, HttpServletRequest request) {
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        String email = jwtTokenProvider.getUsernameFromToken(token);
        Customer customer = customerRepository.findCustomerByEmail(email);
        customer.setFullName(profileCustomerDto.getFullName());
        customer.setPhone(profileCustomerDto.getPhoneNumber());
        customerRepository.save(customer);
    }
    @PutMapping("api/customer/changePassword")
    public @ResponseBody String changePassword(@RequestBody ChangePasswordDto changePasswordDto, HttpServletRequest request) {
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        String email = jwtTokenProvider.getUsernameFromToken(token);
        Customer customer = customerRepository.findCustomerByEmail(email);
        if(!passwordEncoder.matches(changePasswordDto.getOldPassword(),customer.getPassword())) {
            return "Password incorrect";
        } else {
            if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getNewPasswordRepeat())) {
                return "Password not match";
            }
            if (changePasswordDto.getNewPassword().length() < 6) {
                return "Password is too sort";
            }
            customer.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
            customerRepository.save(customer);
            return "ok";
        }
    }

}
