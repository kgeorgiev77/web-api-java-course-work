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
                        .requestMatchers( "/users", "/organizations").hasAnyAuthority("ROLE_ADMIN") // Admin can access users and organizations
                        .requestMatchers( "/events", "/badges").hasAnyAuthority("ROLE_HOST", "ROLE_ORGANIZATION","ROLE_ADMIN") // Host and Organization can access events and badges
                        .requestMatchers( "/users").hasAnyAuthority("ROLE_VOLUNTEER")
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