package com.leocode.securityapi.controller;

import com.leocode.securityapi.models.User;
import com.leocode.securityapi.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

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
}
