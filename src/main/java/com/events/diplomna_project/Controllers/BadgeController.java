package com.events.diplomna_project.Controllers;


import com.events.diplomna_project.Models.BadgeModel;
import com.events.diplomna_project.Repositories.BadgeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class BadgeController {
    private final BadgeRepository badgeRepository;

    public BadgeController(BadgeRepository badgeRepo) {
        badgeRepository = badgeRepo;
    }

    @GetMapping("/badges")
    public List<BadgeModel> getAllBadges(){
        return badgeRepository.findAll();
    }

    @PostMapping("/badges")
    public ResponseEntity<String> createBadge(@RequestBody BadgeModel badge){
        try {
            badgeRepository.save(badge);
            return ResponseEntity.ok("Badge created successfully");
        }catch (Exception e){
            return  ResponseEntity.ofNullable(e.getMessage());
        }
    }

    @DeleteMapping("/badges/{id}")
    public ResponseEntity<String> deleteBadge(@PathVariable Long id) {
        try{
            if (badgeRepository.existsById(id)) {
                badgeRepository.deleteById(id);
                return ResponseEntity.ok("Badge deleted successfully");
            }
            else{
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e){
            return ResponseEntity.ofNullable(e.getMessage());
        }
    }

    @PutMapping("/badges/{id}")
    public ResponseEntity<String> updateBadge(@PathVariable Long id, @RequestBody BadgeModel updatedBadge) {
        try{
            Optional<BadgeModel> existingBadge = badgeRepository.findById(id);
            if (existingBadge.isPresent()) {
                BadgeModel badgeUpdated = existingBadge.get();
                if (updatedBadge.getMessage() != null) {
                    badgeUpdated.setMessage(updatedBadge.getMessage());
                }
                badgeRepository.save(badgeUpdated);
                return ResponseEntity.ok("Badge updated successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        }catch(Exception e){
            return ResponseEntity.ofNullable(e.getMessage());
        }
    }
}
