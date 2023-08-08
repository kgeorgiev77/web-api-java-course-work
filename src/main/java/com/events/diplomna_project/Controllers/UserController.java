package com.events.diplomna_project.Controllers;

import com.events.diplomna_project.Models.UserModel;
import com.events.diplomna_project.Repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepo) {
        userRepository = userRepo;
    }


    @GetMapping(value="/users")
    public List <UserModel> getAllUsers(){
        return userRepository.findAll();
    }

    @PostMapping(value="/users")
    public ResponseEntity<String> createUser(@RequestBody UserModel user){
        try {
            userRepository.save(user);
            return ResponseEntity.ok("User created successfully");
        }catch (Exception e){
            return  ResponseEntity.ofNullable(e.getMessage());
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