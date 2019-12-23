package com.leocode.securityapi.controller;

import com.leocode.securityapi.models.Post;
import com.leocode.securityapi.models.User;
import com.leocode.securityapi.models.request.PostRequest;
import com.leocode.securityapi.repository.PostRepository;
import com.leocode.securityapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
public class PostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/posts")
    public ResponseEntity<?> getAllPosts(HttpServletRequest request, HttpServletResponse response) {
        List<Post> posts = postRepository.findAll();
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, Authorization, X-Requested-With, remember-me");
        return ResponseEntity.ok(posts);

        //return ResponseEntity.ok(posts);
    }

    @PostMapping("/posts")
    public Post savePost(@RequestBody PostRequest postRequest) {

        Post post = null;
        Optional<User> user = userRepository.findById(postRequest.getUserId());

        if(user.isPresent()){
            post = new Post(postRequest);
            post.setUser(user.get());
        }

        return postRepository.save(post);
    }
}
