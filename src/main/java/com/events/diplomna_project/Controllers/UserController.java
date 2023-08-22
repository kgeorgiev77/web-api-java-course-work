package com.events.diplomna_project.Controllers;

import com.events.diplomna_project.Models.BadgeModel;
import com.events.diplomna_project.Models.UserModel;
import com.events.diplomna_project.Repositories.BadgeRepository;
import com.events.diplomna_project.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;



    public UserController(UserRepository userRepository,BadgeRepository badgeRepository) {
        this.userRepository = userRepository;
        this.badgeRepository = badgeRepository;
    }


    @GetMapping(value="/users")
    public List <UserModel> getAllUsers(){

        return userRepository.findAll();
    }

    @GetMapping("/users/{id}/badges")
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
    public ResponseEntity<Optional<UserModel>> searchUsersByName(@RequestParam String name) {
        // Implement the logic to search users by name
        Optional<UserModel> find = userRepository.findByName(name);

        return ResponseEntity.ok(find);
    }

    @PostMapping("/users/{userId}/badges/{badgeId}")
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
    public ResponseEntity<String> createUser(@RequestBody UserModel user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return ResponseEntity.ok("User created successfully");
        } catch (Exception e) {
            return ResponseEntity.ofNullable(e.getMessage());
        }
    }


    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        // Check if the user with the given id exists in the database
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
    public ResponseEntity<String> updateUserProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UserModel updatedUser) {
        String loggedInUserEmail = userDetails.getUsername();

        if (!loggedInUserEmail.equals(updatedUser.getEmail())) {
            return ResponseEntity.badRequest().body("You can only update your own profile");
        }

        Optional<UserModel> currentUserOptional = userRepository.findByEmail(loggedInUserEmail);
        if (currentUserOptional.isPresent()) {
            UserModel currentUser = currentUserOptional.get();

            if (updatedUser.getName() != null) {
                currentUser.setName(updatedUser.getName());
            }
            if (updatedUser.getAge() != 0) {
                currentUser.setAge(updatedUser.getAge());
            }
            if (updatedUser.getDescription() != null) {
                currentUser.setDescription(updatedUser.getDescription());
            }
            if (updatedUser.getEmail() != null) {
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


    @PutMapping(value = "/users/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserModel updatedUser) {
        try{
            Optional<UserModel> existingUser = userRepository.findById(id);
            if (existingUser.isPresent()) {

                UserModel userUpdated = existingUser.get();
                if (updatedUser.getName() != null) {
                    userUpdated.setName(updatedUser.getName());
                }
                if (updatedUser.getAge() != 0) {
                    userUpdated.setAge(updatedUser.getAge());
                }
                if (updatedUser.getDescription() != null) {
                    userUpdated.setDescription(updatedUser.getDescription());
                }
                if (updatedUser.getEmail() != null) {
                    userUpdated.setEmail(updatedUser.getEmail());
                }
                if (updatedUser.getPhone() != null) {
                    userUpdated.setPassword(updatedUser.getPassword());
                }
                if (updatedUser.getRole() != null) {
                    userUpdated.setRole(updatedUser.getRole());
                }
                if (updatedUser.getBadges() != null) {
                    userUpdated.setBadges(updatedUser.getBadges());
                }
                userRepository.save(userUpdated);
                return ResponseEntity.ok("User updated successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        }catch(Exception e){
            return ResponseEntity.ofNullable(e.getMessage());
        }
    }
}