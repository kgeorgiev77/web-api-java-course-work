package com.events.diplomna_project.Service;

import com.events.diplomna_project.Models.OrganizationModel;
import com.events.diplomna_project.Models.UserModel;
import com.events.diplomna_project.Repositories.OrganizationRepository;
import com.events.diplomna_project.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;

    public UserService(UserRepository userRepository, OrganizationRepository organizationRepository) {
        this.userRepository = userRepository;
        this.organizationRepository = organizationRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<UserModel> userOptional = userRepository.findByName(name);
        if (userOptional.isPresent()) {
            UserModel user = userOptional.get();
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                    .password(user.getPassword())
                    .authorities("ROLE_" + user.getRole().toUpperCase())
                    .build();
        }

        Optional<OrganizationModel> organizationOptional = organizationRepository.findByName(name);
        if (organizationOptional.isPresent()) {
            OrganizationModel organization = organizationOptional.get();
            return org.springframework.security.core.userdetails.User
                    .withUsername(organization.getEmail())
                    .password(organization.getPassword())
                    .authorities("ROLE_ORGANIZATION")
                    .build();
        }

        throw new UsernameNotFoundException("User or Organization not found with username: " + name);
    }

}

