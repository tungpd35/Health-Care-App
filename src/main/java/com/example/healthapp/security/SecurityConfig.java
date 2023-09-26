package com.example.healthapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserService userService;
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Password encoder, để Spring Security sử dụng mã hóa mật khẩu người dùng
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http ) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeRequests()
                .requestMatchers("/","*/login","/api/post/filter/**","/staff/login","*/register","/register","/api/getAllPosts","/post/page/**","/api/sendRequest","forgot-password/**","resetPassword/**","/home","/customer/**","login/**","build/**","vendors/**","/posts","/post/*/detail","/api/getAllPosts","/post/**","/api/guestRequest","/avatars/**","/profile","/register/done","/yeu-cau-da-gui").permitAll()
                .requestMatchers("/staff/**","api/deletePost/**","api/request/**", "api/staff/image", "/api/staff/updateInfo","/api/staff/getAvatar").hasRole("STAFF")
                .requestMatchers("/api/customer/**").hasRole("CUSTOMER")
                .anyRequest()
                .authenticated().and().addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class).build();
    }
}
