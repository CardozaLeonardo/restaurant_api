package com.leocode.securityapi;

import com.leocode.securityapi.models.AuthenticationRequest;
import com.leocode.securityapi.models.AuthenticationResponse;
import com.leocode.securityapi.models.Greeting;
import com.leocode.securityapi.services.MyUserDetailsService;
import com.leocode.securityapi.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloResource {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    Logger logger = LoggerFactory.getLogger(HelloResource.class);

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/admin")
    public ResponseEntity<?> greetingAdmin() {

        Greeting gre = new Greeting();
        gre.setTitle("Ola k ase");
        gre.setContent("Eres admin wee!");

        return ResponseEntity.ok(gre);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        logger.info("Authenticate: everything fine here");
        logger.info("Username: " + authenticationRequest.getUsername());
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }catch(BadCredentialsException e){
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        //System.out.println("AUthenticate: " + userDetails.getUsername());
        logger.info("Authenticate: ----");

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));

    }
}
