package com.example.healthapp.controllers;

import com.example.healthapp.models.*;
import com.example.healthapp.repositories.*;
import com.example.healthapp.security.JwtAuthenticationFilter;
import com.example.healthapp.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class RequestController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private HealthStaffRepository healthStaffRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    @PostMapping("api/sendRequest")
    public @ResponseBody void sendRequest(HttpServletRequest httpServletRequest) {
        String token = jwtAuthenticationFilter.getJwtFromRequest(httpServletRequest);
        String email = jwtTokenProvider.getUsernameFromToken(token);
        Long postId = Long.valueOf(httpServletRequest.getHeader("postId"));
        System.out.println(email + postId);
        Customer customer = customerRepository.findCustomerByEmail(email);
        Post post = postRepository.getPostById(postId);
        Date date = new Date();
        Request request = new Request(date, Status.WAITING, customer, post);
        List<Request> requests = customer.getRequest();
        requests.add(request);
        customer.setRequest(requests);
        List<Request> requests1 = post.getRequest();
        requests1.add(request);
        post.setRequest(requests1);
        Notification notification = new Notification("Bạn có một yêu cầu mới", customer.getFullName() + " vừa gửi cho bạn một yêu cầu",false,post.getHealthStaff());
        notificationRepository.save(notification);
        post.getHealthStaff().getNotification().add(notification);
        requestRepository.save(request);
    }

    @GetMapping("/staff/requests")
    public String requestPage(Model model, HttpServletRequest request) {
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        String email = jwtTokenProvider.getUsernameFromToken(token);
        HealthStaff user = healthStaffRepository.findHealthStaffByEmail(email);
        List<Request> requests = new ArrayList<>();
        List<Post> post = user.getPosts();
        for (Post value : post) {
            requests.addAll(value.getRequest());
        }
        List<Notification> notices = user.getNotification();
        int count = 0;
        for(int i=0; i < notices.size() ; i++) {
            if(!notices.get(i).isSeen()) {
                count++;
            }
        }
        model.addAttribute("notices", notices);
        model.addAttribute("quantity", count);
        model.addAttribute("requests", requests);
        model.addAttribute("staff", user);
        return "listrequest";
    }

    @GetMapping("staff/post/request")
    public String page(Model model, HttpServletRequest request) {
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        String email = jwtTokenProvider.getUsernameFromToken(token);
        HealthStaff healthStaff = healthStaffRepository.findHealthStaffByEmail(email);
        model.addAttribute("posts", healthStaff.getPosts());
        model.addAttribute("staff", healthStaff);
        return "requestbypost";
    }

    @PutMapping("api/request/{id}/accept")
    public @ResponseBody void acceptRequest(@PathVariable Long id, HttpServletRequest request) {
        Request request1 = requestRepository.findRequestById(id);
        request1.setStatus(Status.ACCEPTED);
        requestRepository.save(request1);
    }

    @PutMapping("api/request/{id}/deny")
    public @ResponseBody void denyRequest(@PathVariable Long id, HttpServletRequest request) {
        Request request1 = requestRepository.findRequestById(id);
        request1.setStatus(Status.DENY);
        requestRepository.save(request1);
    }

    @PostMapping("api/guestRequest")
    public @ResponseBody void guestRequest(@RequestBody Customer guest, HttpServletRequest httpServletRequest) {
        System.out.println(guest.getFullName() + guest.getPhone());
        Long postId = Long.valueOf(httpServletRequest.getHeader("postId"));
        Post post = postRepository.getPostById(postId);
        Date date = new Date();
        Request request = new Request(date, Status.WAITING, guest, post);
        List<Request> requests1 = post.getRequest();
        requests1.add(request);
        post.setRequest(requests1);
        customerRepository.save(guest);
        requestRepository.save(request);
    }

    @DeleteMapping("api/staff/deleteRequest/{id}")
    public @ResponseBody void deleteRequest(@PathVariable Long id, HttpServletRequest request) {
        Request request1 = requestRepository.findRequestById(id);
        Post post = request1.getPost();
        List<Request> request2 = post.getRequest();
        request2.remove(request1);
        postRepository.save(post);
    }
    @GetMapping("/yeu-cau-da-gui")
    public String requested(HttpServletRequest request, Model model) {
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        String email = jwtTokenProvider.getUsernameFromToken(token);
        Customer customer = customerRepository.findCustomerByEmail(email);
        List<Request> requests = customer.getRequest();
        model.addAttribute("requests", requests);
        return "requested";
    }
}