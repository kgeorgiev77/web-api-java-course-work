package com.events.diplomna_project.Controllers;

import com.events.diplomna_project.Models.OrganizationModel;
import com.events.diplomna_project.Repositories.OrganizationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping
public class OrganizationController {
    private final OrganizationRepository organizationRepository;

    public OrganizationController(OrganizationRepository organizationRepo) {
        organizationRepository = organizationRepo;
    }

    @GetMapping("/organizations")
    public List<OrganizationModel> getAllOrganizations(){
        return organizationRepository.findAll();
    }

    @PostMapping("/organizations")
    public ResponseEntity<String> createOrganization(@RequestBody OrganizationModel organization){
        try {
            organizationRepository.save(organization);
            return ResponseEntity.ok("Organization created successfully");
        }catch (Exception e){
            return  ResponseEntity.ofNullable(e.getMessage());
        }
    }

    @DeleteMapping("/organizations/{id}")
    public ResponseEntity<String> deleteOrganization(@PathVariable Long id) {
        try{
            if (organizationRepository.existsById(id)) {
                organizationRepository.deleteById(id);
                return ResponseEntity.ok("Organization deleted successfully");
            }
            else{
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e){
            return ResponseEntity.ofNullable(e.getMessage());
        }
    }

    @PutMapping("/organizations/{id}")
    public ResponseEntity<String> updateOrganization(@PathVariable Long id, @RequestBody OrganizationModel updatedOrganization) {
        try{
            Optional<OrganizationModel> existingOrganization = organizationRepository.findById(id);
            if (existingOrganization.isPresent()) {
                OrganizationModel organizationUpdated = existingOrganization.get();
                organizationUpdated.setName(updatedOrganization.getName());
                organizationUpdated.setDescription(updatedOrganization.getDescription());
                organizationUpdated.setPassword(updatedOrganization.getPassword());
                return ResponseEntity.ok("Organization updated successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        }catch(Exception e){
            return ResponseEntity.ofNullable(e.getMessage());
        }
    }

}
