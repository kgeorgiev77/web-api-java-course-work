package com.events.diplomna_project.Config;

import com.events.diplomna_project.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    @Lazy
    public SecurityConfig(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers(String.valueOf(PathRequest.toStaticResources().atCommonLocations())).permitAll() // Allow static resources
                        .requestMatchers("/organizations/{organizationId}/approve").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/organizations/search", "events/search", "/events").hasAnyAuthority("ROLE_ORGANIZATIONS","ROLE_ADMIN","ROLE_VOLUNTEER"," ROLE_HOST")
                        .requestMatchers("/organizations","/organizations/{id}/events","/organizations/register","/organizations/{id}").hasAnyAuthority("ROLE_ADMIN","ROLE_ORGANIZATIONS") // Admin can access users and organizations
                        .requestMatchers( "/events","/users/{userId}/badges/{badgeId}", "/events/{eventId}/volunteers").hasAnyAuthority("ROLE_HOST", "ROLE_ORGANIZATION","ROLE_ADMIN") // Host and Organization can access events and badges
                        .requestMatchers( "/users","/users/{id}","/users/me","/users/register","/users/search","/users/{userId}/events").hasAnyAuthority("ROLE_VOLUNTEER","ROLE_ADMIN")
                        .requestMatchers("/events/{id}").hasAnyAuthority("ROLE_HOST","ROLE_ADMIN")
                        .requestMatchers("/badges","/badges/{id}").hasAnyAuthority("ROLE_HOST ","ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Lazy
    public void authenticationManagerConfigurer(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }


}