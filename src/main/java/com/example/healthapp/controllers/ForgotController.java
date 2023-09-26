package com.example.healthapp.controllers;

import com.example.healthapp.models.Customer;
import com.example.healthapp.models.PasswordResetToken;
import com.example.healthapp.models.User;
import com.example.healthapp.repositories.CustomerRepository;
import com.example.healthapp.repositories.PasswordTokenRepository;
import com.example.healthapp.repositories.UserRepository;
import com.example.healthapp.security.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Locale;
import java.util.UUID;

@Controller
@RequestMapping
@CrossOrigin
public class ForgotController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordTokenRepository passwordTokenRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @GetMapping("/forgot-password")
    public String forgotPage() {
        return "forgot";
    }
    @PutMapping("/forgot-password")
    public @ResponseBody String resetPassword(HttpServletResponse response, @RequestParam String  email) throws Exception {
        System.out.println(email);
        if(customerRepository.findCustomerByEmail(email) == null) {
            throw new NullPointerException();
        } else {
            Customer user = customerRepository.findCustomerByEmail(email);
            String token = UUID.randomUUID().toString();
            if(user.getPasswordResetToken() == null) {
                PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
                passwordTokenRepository.save(passwordResetToken);
                user.setPasswordResetToken(passwordResetToken);
                customerRepository.save(user);
            } else {
                user.getPasswordResetToken().setToken(token);
                customerRepository.save(user);
            }
            String resetLink = "http://localhost:8080/resetPassword/" + token;
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom("phamductung69@gmail.com");
            msg.setTo(email);
            msg.setSubject("Password Reset");
            msg.setText("Bạn nhận được e-mail này vì bạn hoặc ai đó đã yêu cầu đặt lại mật khẩu cho tài khoản của bạn.\n\n" +
                         "Nhấp vào liên kết bên dưới để đặt lại mật khẩu của bạn.\n" + resetLink + "\n\nNếu đây không phải yêu cầu của bạn, vui lòng bỏ qua email này.");
            javaMailSender.send(msg);
            return "oke";
        }
    }
    @GetMapping("/forgot-password/done")
    public String forgotDone() {
        return "done";
    }
    @GetMapping("/resetPassword/{token}")
    public String resetPage(@PathVariable String token) throws Exception {
        System.out.println(token);
        PasswordResetToken passwordResetToken = passwordTokenRepository.findPasswordResetTokenByToken(token);
        if(passwordResetToken == null) {
            throw new Exception();
        }
       return "resetpassword";
    }
    @PutMapping("/resetPassword/{token}/{password}")
    public @ResponseBody String resetPass(@PathVariable String token, @PathVariable String password) throws Exception {
        PasswordResetToken passwordResetToken = passwordTokenRepository.findPasswordResetTokenByToken(token);
        if(passwordResetToken == null) {
            throw new Exception();
        }
        User user = passwordResetToken.getUser();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return "ok";
    }
    @GetMapping("/resetPassword/done")
    public String resetDone() {return "resetdone";}
}
