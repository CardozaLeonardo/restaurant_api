package com.leocode.securityapi.controller;

import com.leocode.securityapi.models.Post;
import com.leocode.securityapi.models.User;
import com.leocode.securityapi.models.request.PostRequest;
import com.leocode.securityapi.repository.PostRepository;
import com.leocode.securityapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<?> getAllPosts() {
        List<Post> posts = postRepository.findAll();

        return ResponseEntity.ok(posts);

        //return ResponseEntity.ok(posts);
    }

    @GetMapping("/get_posts_by_user/{user}")
    public ResponseEntity<?> getPostsByUser(@PathVariable("user") int userId) {
        Optional<User> user = userRepository.findById(userId);

        if(!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<Post> posts = postRepository.getPostsByUser(user.get());

        return ResponseEntity.ok(posts);
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
