package com.example.healthapp.controllers;

import com.example.healthapp.dto.FilterPost;
import com.example.healthapp.dto.PostDto;
import com.example.healthapp.models.*;
import com.example.healthapp.repositories.*;
import com.example.healthapp.security.JwtAuthenticationFilter;
import com.example.healthapp.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping
@CrossOrigin
public class PostController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private HealthStaffRepository healthStaffRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CustomerRepository customerRepository;
    private JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();
    @GetMapping("/api/staff/getPosted")
    public @ResponseBody List<Post> getPosted(HttpServletRequest request) {
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        String email = jwtTokenProvider.getUsernameFromToken(token);
        HealthStaff healthStaff = healthStaffRepository.findHealthStaffByEmail(email);
        return postRepository.getAllByHealthStaff(healthStaff);
    }
    @GetMapping("/staff/newPost")
    public String newPostPage(Model model, HttpServletRequest request) {
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        String email = jwtTokenProvider.getUsernameFromToken(token);
        HealthStaff healthStaff = healthStaffRepository.findHealthStaffByEmail(email);
        List<Notification> notices = healthStaff.getNotification();
        int count = 0;
        for(int i=0; i < notices.size() ; i++) {
            if(!notices.get(i).isSeen()) {
                count++;
            }
        }
        model.addAttribute("notices", notices);
        model.addAttribute("quantity", count);
        model.addAttribute("staff",healthStaff);

        return "newpost";
    }

    @PostMapping("/staff/newPost")
    public @ResponseBody Post newPost(@RequestBody PostDto postDto, HttpServletRequest request, Model model) {
        Service service = serviceRepository.findServiceById(postDto.getServiceId());
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            bearerToken = bearerToken.substring(7);
            System.out.println(bearerToken);
        }
        String email = jwtTokenProvider.getUsernameFromToken(bearerToken);
        HealthStaff healthStaff = healthStaffRepository.findHealthStaffByEmail(email);

        Address address = postDto.getLocation();
        addressRepository.save(address);
        postDto.setDate(new Date());
        Post newPost = new Post(postDto.getTitle(), postDto.getDescription(), postDto.getSkills(), postDto.getExperience(), postDto.getDate(), postDto.getEmail(), postDto.getPhoneNumber(), address, service, healthStaff);
        List<Post> posts = healthStaff.getPosts();
        posts.add(newPost);
        System.out.println(healthStaff.getPosts());
        address.setPost(newPost);
        service.getPosts().add(newPost);
        postRepository.save(newPost);
        return newPost;
    }

    @GetMapping("/staff/getAllPost")
    public @ResponseBody List<Post> getAllPost(@RequestHeader("Authorization") String token) {
        String email = jwtTokenProvider.getUsernameFromToken(token.substring(7));
        return healthStaffRepository.findHealthStaffByEmail(email).getPosts();
    }

    @GetMapping("/posts")
    public String postsPage(Model model) {
        Page<Post> posts = postRepository.findAllByOrderByDateDesc(PageRequest.of(0, 5));
        model.addAttribute("posts", posts.getContent());
        model.addAttribute("totalPage", posts.getTotalPages());
        return "posts";
    }

    @GetMapping("/api/getAllPosts")
    public @ResponseBody List<Post> getPosts() {
        List<Post> post = postRepository.findAll();
        post.sort(new Comparator<Post>() {
            @Override
            public int compare(Post post, Post t1) {
                return t1.getRequest().size() - post.getRequest().size();
            }
        });
        return post;
    }

    @GetMapping("/post/page/{id}")
    public String pagePosts(@PathVariable int id, Model model) {
        Page<Post> posts = postRepository.findAll(PageRequest.of(id - 1, 5));
        model.addAttribute("serviceId",0);
        model.addAttribute("currentPage",id);
        model.addAttribute("posts", posts.getContent());
        model.addAttribute("totalPage", posts.getTotalPages());
        return "posts";
    }
    @GetMapping("/post/page/{id}/search/{keyword}")
    public String searchPost(@PathVariable int id,@PathVariable String keyword, Model model) {
        Page<Post> posts = postRepository.searchAllByKeyWord(keyword,PageRequest.of(id - 1, 5));
        model.addAttribute("serviceId",0);
        model.addAttribute("currentPage",id);
        model.addAttribute("posts", posts.getContent());
        model.addAttribute("totalPage", posts.getTotalPages());
        return "posts";
    }
    @GetMapping("/post/page/{id}/{sortBy}")
    public String sortPosts(@PathVariable int id,@PathVariable String sortBy , Model model) throws Exception {
        if(sortBy.equals("new")) {
            Page<Post> posts = postRepository.findAllByOrderByDateDesc(PageRequest.of(id - 1, 5));
            model.addAttribute("currentPage",id);
            model.addAttribute("posts", posts.getContent());
            model.addAttribute("sortBy", "Mới Nhất");
            model.addAttribute("serviceId",0);
            model.addAttribute("totalPage", posts.getTotalPages());
            return "posts";
        }
        else if(sortBy.equals("popular")) {
            Page<Post> posts = postRepository.findAllByOrderByTotalRequestDesc(PageRequest.of(id - 1, 5));
            model.addAttribute("posts", posts.getContent());
            model.addAttribute("currentPage",id);
            model.addAttribute("sortBy", "Phổ Biến");
            model.addAttribute("totalPage", posts.getTotalPages());
            return "posts";
        }
        else {
            throw new Exception();
        }
    }
    @GetMapping("/post/service/{serviceId}/page/{id}")
    public  String filByService(@PathVariable Long serviceId, @PathVariable int id, Model model) {
        Page<Post> posts = postRepository.findAllByServiceId(serviceId, PageRequest.of(id - 1, 5));
        model.addAttribute("posts", posts.getContent());
        model.addAttribute("sortBy", "Phổ Biến");
        model.addAttribute("currentPage",id);
        model.addAttribute("serviceId",serviceId);
        model.addAttribute("totalPage", posts.getTotalPages());
        return "posts";
    }
    @GetMapping("/post/service/{serviceId}/page/{id}/{sortBy}")
    public String filByServiceAndSort(@PathVariable Long serviceId, @PathVariable int id, @PathVariable String sortBy, Model model) throws Exception {
        if(sortBy.equals("new")) {
            Page<Post> posts = postRepository.findAllByServiceIdAndSortByDate(serviceId, PageRequest.of(id - 1, 5));
            model.addAttribute("posts", posts.getContent());
            model.addAttribute("sortBy", "Mới nhất");
            model.addAttribute("serviceId",serviceId);
            model.addAttribute("currentPage",id);
            model.addAttribute("totalPage", posts.getTotalPages());
            return "posts";
        }
        else if(sortBy.equals("popular")) {
            Page<Post> posts = postRepository.findAllByServiceIdAndSortByPopular(serviceId, PageRequest.of(id - 1, 5));
            model.addAttribute("posts", posts.getContent());
            model.addAttribute("sortBy", "Phổ Biến");
            model.addAttribute("currentPage",id);
            model.addAttribute("serviceId",serviceId);
            model.addAttribute("totalPage", posts.getTotalPages());
            return "posts";
        } else {
            throw new Exception();
        }
    }
    @GetMapping("/post/city/{cityName}/page/{id}")
    public String fillByCity(@PathVariable String cityName, @PathVariable int id, Model model) {
        Page<Post> posts = postRepository.getAllByCity(cityName, PageRequest.of(id - 1, 5));
        model.addAttribute("posts", posts.getContent());
        model.addAttribute("sortBy", "Mới nhất");
        model.addAttribute("currentPage",id);
        model.addAttribute("serviceId",0);
        model.addAttribute("cityName", cityName);
        model.addAttribute("totalPage", posts.getTotalPages());
        return "posts";
    }
    @GetMapping("/post/city/{cityName}/page/{id}/{sortBy}")
    public String fillByCitySort(@PathVariable String cityName, @PathVariable int id,@PathVariable String sortBy, Model model) throws Exception{
        if(sortBy.equals("new")) {
            Page<Post> posts = postRepository.getAllByCitySortByDate(cityName, PageRequest.of(id - 1, 5));
            model.addAttribute("posts", posts.getContent());
            model.addAttribute("sortBy", "Mới nhất");
            model.addAttribute("currentPage",id);
            model.addAttribute("serviceId",0);
            model.addAttribute("totalPage", posts.getTotalPages());
            return "posts";
        }
        else if(sortBy.equals("popular")) {
            Page<Post> posts = postRepository.getAllByCitySortByPopular(cityName, PageRequest.of(id - 1, 5));
            model.addAttribute("posts", posts.getContent());
            model.addAttribute("sortBy", "Phổ Biến");
            model.addAttribute("serviceId",0);
            model.addAttribute("currentPage",id);
            model.addAttribute("totalPage", posts.getTotalPages());
            return "posts";
        } else {
            throw new Exception();
        }
    }
    @GetMapping("/post/service/{serviceId}/city/{cityName}/page/{id}")
    public String filAndSortByServiceIdAndCity(@PathVariable Long serviceId, @PathVariable String cityName, @PathVariable int id, Model model) {
        Page<Post> posts = postRepository.getAllByCityAndService(String.valueOf(serviceId),cityName, PageRequest.of(id - 1, 5));
        model.addAttribute("posts", posts.getContent());
        model.addAttribute("sortBy", "Phổ Biến");
        model.addAttribute("serviceId",serviceId);
        model.addAttribute("currentPage",id);
        model.addAttribute("totalPage", posts.getTotalPages());
        return "posts";
    }
    @GetMapping("/post/service/{serviceId}/city/{cityName}/page/{id}/{sortBy}")
    public String filAndSortByServiceIdAndCity(@PathVariable Long serviceId, @PathVariable String cityName, @PathVariable int id,@PathVariable String sortBy, Model model) throws Exception{
        if(sortBy.equals("new")) {
            Page<Post> posts = postRepository.getAllByCityAndServiceSortByDate(String.valueOf(serviceId),cityName, PageRequest.of(id - 1, 5));
            model.addAttribute("posts", posts.getContent());
            model.addAttribute("currentPage",id);
            model.addAttribute("sortBy", "Phổ Biến");
            model.addAttribute("serviceId",serviceId);
            model.addAttribute("totalPage", posts.getTotalPages());
            return "posts";
        }
       else if(sortBy.equals("popular")) {
            Page<Post> posts = postRepository.getAllByCityAndService(String.valueOf(serviceId),cityName, PageRequest.of(id - 1, 5));
            model.addAttribute("posts", posts.getContent());
            model.addAttribute("sortBy", "Phổ Biến");
            model.addAttribute("serviceId",serviceId);
            model.addAttribute("currentPage",id);
            model.addAttribute("totalPage", posts.getTotalPages());
            return "posts";
        } else {
           throw new Exception();
        }
    }
    @GetMapping("/post/{id}/detail")
    public String postDetail(@PathVariable Long id, Model model) {
        Post post = postRepository.getPostById(id);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(post.getDate());
        model.addAttribute(post);
        model.addAttribute("date", date);
        return "postdetail";
    }

    @GetMapping("/staff/listPosted")
    public String lisPost(Model model, HttpServletRequest request) {
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        String email = jwtTokenProvider.getUsernameFromToken(token);
        HealthStaff healthStaff = healthStaffRepository.findHealthStaffByEmail(email);
        List<Notification> notices = healthStaff.getNotification();
        int count = 0;
        for(int i=0; i < notices.size() ; i++) {
            if(!notices.get(i).isSeen()) {
                count++;
            }
        }
        model.addAttribute("notices", notices);
        model.addAttribute("quantity", count);
        model.addAttribute("posts", healthStaff.getPosts());
        model.addAttribute("staff", healthStaff);
        return "posted";
    }

    @DeleteMapping("api/deletePost/{id}")
    public @ResponseBody void deletePost(HttpServletRequest request, @PathVariable Long id) {
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        String email = jwtTokenProvider.getUsernameFromToken(token);
        HealthStaff healthStaff = healthStaffRepository.findHealthStaffByEmail(email);
        postRepository.deletePostByIdAndUserId(id, healthStaff.getId());
    }

    @GetMapping("/api/post/filter/{sortId}")
    public @ResponseBody List<Post> filPost(@RequestBody FilterPost filterPost, @PathVariable int sortId) {
        if (filterPost.getCity().equals("Tất Cả")) {
            filterPost.setCity("%%");
        }
        if(filterPost.getServiceId().equals("Tất Cả")) {
            filterPost.setServiceId("%%");
        }
        List<Post> post = postRepository.getAllByServiceIdAndCity(filterPost.getServiceId(), filterPost.getCity());
        if(sortId == 1) {
            post.sort(new Comparator<Post>() {
                @Override
                public int compare(Post post, Post t1) {
                    return t1.getRequest().size() - post.getRequest().size();
                }
            });
            return post;
        }
        else {
            post.sort(new Comparator<Post>() {
                @Override
                public int compare(Post post, Post t1) {
                    return t1.getDate().compareTo(post.getDate());
                }
            });
            return post;
        }
    }
}