package com.example.healthapp.security;

import com.example.healthapp.models.CustomUserDetails;
import com.example.healthapp.models.Customer;
import com.example.healthapp.models.PasswordResetToken;
import com.example.healthapp.models.User;
import com.example.healthapp.repositories.CustomerRepository;
import com.example.healthapp.repositories.PasswordTokenRepository;
import com.example.healthapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordTokenRepository passwordTokenRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(user);
    }
    public void createPasswordResetTokenForUser(Customer user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }
}
