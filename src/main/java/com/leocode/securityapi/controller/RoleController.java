package com.leocode.securityapi.controller;

import com.leocode.securityapi.models.User;
import com.leocode.securityapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class RoleController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/get_user_roles/{user}")
    public ResponseEntity<?> getUserRole(@PathVariable("user") int idUser) {

        //Optional<User>
        return null;
    }
}
