package com.example.healthapp;

import com.example.healthapp.models.Customer;
import com.example.healthapp.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@Configuration
public class HealthAppApplication {
//	@Autowired
//	CustomerRepository customerRepository;
//	@Autowired
//	PasswordEncoder passwordEncoder;
//	@Bean
//	public void Test() {
//		Customer customer = new Customer("tung", passwordEncoder.encode("123"));
//		customerRepository.save(customer);
//	}
	public static void main(String[] args) {
		SpringApplication.run(HealthAppApplication.class, args);
	}

}
