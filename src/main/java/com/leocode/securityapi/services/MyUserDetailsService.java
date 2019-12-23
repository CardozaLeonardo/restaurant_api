package com.leocode.securityapi.services;


import com.leocode.securityapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //return new User("foo", "foo", new ArrayList<>());
        Optional<com.leocode.securityapi.models.User> user = userRepository.findByUsername(userName);

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));

        return new User(user.get().getUsername(), user.get().getPassword(), new ArrayList<>());




        //return user.map(MyUserDetails::new).get();
        //return new MyUserDetails(user.get());
    }
}
