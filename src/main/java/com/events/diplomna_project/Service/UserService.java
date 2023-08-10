package com.events.diplomna_project.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.events.diplomna_project.Models.UserModel;
import com.events.diplomna_project.Repositories.UserRepository;


public class UserService {
    /*@Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findUser.orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        return org.springframework.security.core.userdetails.User.builder()
                .username(userModel.getName())
                .password(userModel.getPassword()) // Make sure your User entity has a password field
                .roles(userModel.getRole()) // Adjust this according to your User entity structure
                .build();
    }*/
}
