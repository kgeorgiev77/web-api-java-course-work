package com.events.diplomna_project.Controllers;

import com.events.diplomna_project.Models.BadgeModel;
import com.events.diplomna_project.Models.EventModel;
import com.events.diplomna_project.Models.UserModel;
import com.events.diplomna_project.Repositories.BadgeRepository;
import com.events.diplomna_project.Repositories.EventRepository;
import com.events.diplomna_project.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;
    private final EventRepository eventRepository;



    public UserController(UserRepository userRepository,BadgeRepository badgeRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.badgeRepository = badgeRepository;
        this.eventRepository = eventRepository;
    }


    @GetMapping(value="/users")
    //'ROLE_ADMIN'
    public List <UserModel> getAllUsers(){

        return userRepository.findAll();
    }

    @GetMapping("/users/{id}/badges")
    //'ROLE_VOLUNTEER','ROLE_ADMIN'
    public ResponseEntity<List<BadgeModel>> getUserBadges(@PathVariable Long id) {
        Optional<UserModel> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            UserModel user = optionalUser.get();
            List<BadgeModel> badges = user.getBadges();
            return ResponseEntity.ok(badges);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users/search")
    //'ROLE_VOLUNTEER', 'ROLE_ADMIN'
    public ResponseEntity<Optional<UserModel>> searchUsersByName(@RequestParam String name) {
        // Implement the logic to search users by name
        Optional<UserModel> find = userRepository.findByName(name);

        return ResponseEntity.ok(find);
    }

    @PostMapping("/users/{userId}/badges/{badgeId}")
    //'ROLE_HOST','ROLE_ORGANIZATION', 'ROLE_ADMIN'
    public ResponseEntity<String> giveBadgeToUser(
            @PathVariable Long userId,
            @PathVariable Long badgeId) {
        Optional<UserModel> optionalUser = userRepository.findById(userId);
        Optional<BadgeModel> optionalBadge = badgeRepository.findById(badgeId);

        if (optionalUser.isPresent() && optionalBadge.isPresent()) {
            UserModel user = optionalUser.get();
            BadgeModel badge = optionalBadge.get();

            // Add the badge to the user's badge list
            user.getBadges().add(badge);
            userRepository.save(user);

            return ResponseEntity.ok("Badge given to user successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping(value="/users/register")
    //'ROLE_VOLUNTEER','ROLE_ADMIN'
    public ResponseEntity<String> createUser(@RequestBody UserModel user) {
        try {
            Pattern pattern = Pattern.compile("[0-9!@#$%^&*()_+=<>?/{}\\[\\]|~]");
            Matcher matcher = pattern.matcher(user.getPassword());
            if (user.getEmail().length() < 8 || user.getPassword().length() < 8 || !user.getEmail().contains("@") || !matcher.find()){
                return ResponseEntity.ofNullable("Could not create a user because either the email or the password is not valid!");
            }
            else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);
                return ResponseEntity.ok("User created successfully");
            }
        } catch (Exception e) {
            return ResponseEntity.ofNullable(e.getMessage());
        }
    }


    @DeleteMapping(value = "/users/{id}")
   //'ROLE_VOLUNTEER', 'ROLE_ADMIN'
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {

        try{

            if (userRepository.existsById(id)) {
                userRepository.deleteById(id);
                return ResponseEntity.ok("User deleted successfully");
            }
            else{
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e){
            return ResponseEntity.ofNullable(e.getMessage());
        }
    }

    @PutMapping("/users/me")
    //'ROLE_VOLUNTEER', 'ROLE_ADMIN'
    public ResponseEntity<String> updateUserProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UserModel updatedUser) {
        String loggedInUserEmail = userDetails.getUsername();

        if (!loggedInUserEmail.equals(updatedUser.getEmail())) {
            return ResponseEntity.badRequest().body("You can only get your own profile's info");
        }

        Optional<UserModel> currentUserOptional = userRepository.findByEmail(loggedInUserEmail);
        if (currentUserOptional.isPresent()) {
            UserModel currentUser = currentUserOptional.get();

            if (updatedUser.getName() != null && updatedUser.getName().length() > 4) {
                currentUser.setName(updatedUser.getName());
            }
            if (updatedUser.getAge() != 0) {
                currentUser.setAge(updatedUser.getAge());
            }
            if (updatedUser.getDescription() != null) {
                currentUser.setDescription(updatedUser.getDescription());
            }
            if (updatedUser.getEmail() != null && updatedUser.getEmail().length() > 8 && updatedUser.getEmail().contains("@")) {
                currentUser.setEmail(updatedUser.getEmail());
            }
            if (updatedUser.getPhone() != null) {
                currentUser.setPassword(updatedUser.getPassword());
            }
            if (updatedUser.getRole() != null) {
                currentUser.setRole(updatedUser.getRole());
            }
            if (updatedUser.getBadges() != null) {
                currentUser.setBadges(updatedUser.getBadges());
            }
            userRepository.save(currentUser);
            return ResponseEntity.ok("User updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/users/{userId}/enroll/{eventId}")
    //'ROLE_VOLUNTEER','ROLE_HOST', 'ROLE_ADMIN'
    public ResponseEntity<String> enrollUserInEvent(@PathVariable Long userId, @PathVariable Long eventId) {
        Optional<UserModel> optionalUser = userRepository.findById(userId);
        Optional<EventModel> optionalEvent = eventRepository.findById(eventId);

        if (optionalUser.isPresent() && optionalEvent.isPresent()) {
            UserModel user = optionalUser.get();
            EventModel event = optionalEvent.get();

            // Add the event to the user's enrolled events list
            user.getEvents().add(event);
            userRepository.save(user);

            return ResponseEntity.ok("User enrolled in event successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/users/{userId}/events")
    //'ROLE_VOLUNTEER', 'ROLE_ADMIN'
    public ResponseEntity<List<EventModel>> getUserEvents(@PathVariable Long userId) {


        Optional<UserModel> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            UserModel user = optionalUser.get();
            List<EventModel> events = user.getEvents();
            return ResponseEntity.ok(events);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}