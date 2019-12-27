package com.leocode.securityapi.controller;

import com.leocode.securityapi.models.User;
import com.leocode.securityapi.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/users")
    public ResponseEntity<?> showAllUsers() {
        //List<User> users = userRepository.findByUsername("LeoCode");
        Optional<User> users = userRepository.findByUsername("LeoCode");

        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<?> showAllUsers(@PathVariable(value = "username") String username, HttpServletResponse response) {
        Optional<User> users = userRepository.findByUsername(username);

        if(!users.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        }
        logger.info("Obteniendo usuarios...");

        Cookie ck = new Cookie("data", "lalalala");
        response.addCookie(ck);

        return ResponseEntity.ok(users);


    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody User user) {

        String plainPass = user.getPassword();
        user.setPassword(passwordEncoder.encode(plainPass));
        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(user));
    }
}
