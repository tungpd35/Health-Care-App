package com.example.healthapp.controllers;

import com.example.healthapp.models.Customer;
import com.example.healthapp.models.HealthStaff;
import com.example.healthapp.models.Role;
import com.example.healthapp.models.User;
import com.example.healthapp.payload.Response;
import com.example.healthapp.repositories.CustomerRepository;
import com.example.healthapp.repositories.HealthStaffRepository;
import com.example.healthapp.repositories.UserRepository;
import com.example.healthapp.security.JwtTokenProvider;
import com.example.healthapp.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

@Controller
@RequestMapping
@CrossOrigin
public class RegisterController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private HealthStaffRepository healthStaffRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }
    @PostMapping("/register")
    public void register(@RequestBody Customer user) throws Exception {
        System.out.println("oke");
        if(customerRepository.findCustomerByEmail(user.getEmail()) != null) {
            throw new Exception();
        } else {
           Customer newCustomer = new Customer(user.getFullName(), user.getPhone(), user.getEmail(), passwordEncoder.encode(user.getPassword()), Role.CUSTOMER);
           customerRepository.save(newCustomer);
        }
    }
    @GetMapping("/staff/register")
    public String registerStaffPage() {
        return "staffregister";
    }
    @PostMapping("/staff/register")
    public @ResponseBody User registerStaff(@RequestBody HealthStaff user) throws Exception {
        if(customerRepository.findCustomerByEmail(user.getEmail()) != null) {
            throw new Exception();
        } else {
            HealthStaff newUser = new HealthStaff(user.getFullName(),user.getEmail(), passwordEncoder.encode(user.getPassword()), Role.STAFF);
            healthStaffRepository.save(newUser);
            return newUser;
        }
    }
    @GetMapping("/register/done")
    public String registerDone() {
        return "registerdone";
    }

}
