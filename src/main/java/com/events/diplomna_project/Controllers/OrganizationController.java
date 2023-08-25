package com.events.diplomna_project.Controllers;

import com.events.diplomna_project.Models.BadgeModel;
import com.events.diplomna_project.Models.EventModel;
import com.events.diplomna_project.Models.OrganizationModel;
import com.events.diplomna_project.Models.UserModel;
import com.events.diplomna_project.Repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.events.Event;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestController
@RequestMapping
public class OrganizationController {
    private final OrganizationRepository organizationRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public OrganizationController(OrganizationRepository organizationRepo, PasswordEncoder passwordEncoder) {
        this.organizationRepository = organizationRepo;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/organizations")
    //'ROLE_ORGANIZATION', 'ROLE_ADMIN'
    public List<OrganizationModel> getAllOrganizations(){
        return organizationRepository.findAll();
    }

    @GetMapping("/organizations/{id}/events")
    //'ROLE_ORGANIZATION
    public ResponseEntity<List<EventModel>> getOrganizationEvents(@PathVariable Long id) {
        Optional<OrganizationModel> optionalOrg = organizationRepository.findById(id);
        if (optionalOrg.isPresent()) {
            OrganizationModel or = optionalOrg.get();
            List<EventModel> events = or.getEvents();
            return ResponseEntity.ok(events);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/organizations/register")
    //'ROLE_ORGANIZATION', 'ROLE_ADMIN'
    public ResponseEntity<String> createOrganization(@RequestBody OrganizationModel organization){
        try {
            Pattern pattern = Pattern.compile("[0-9!@#$%^&*()_+=<>?/{}\\[\\]|~]");
            Matcher matcher = pattern.matcher(organization.getPassword());
            if (organization.getEmail().length() < 8 || organization.getPassword().length() < 8 || !organization.getEmail().contains("@") || !matcher.find()){
                return ResponseEntity.ofNullable("Could not create an organization because either the email or the password is not valid!");
            }
            else {
                organization.setPassword(passwordEncoder.encode(organization.getPassword()));
                organizationRepository.save(organization);
                return ResponseEntity.ok("Organization created successfully");
            }
        }catch (Exception e){
            return  ResponseEntity.ofNullable(e.getMessage());
        }
    }

    @PostMapping("/organizations/{organizationId}/approve")
    //'ROLE_ADMIN'
    public ResponseEntity<String> approveOrganization(@PathVariable Long organizationId) {
        // Find the organization by ID
        Optional<OrganizationModel> organization = organizationRepository.findById(organizationId);

        if (organization.isPresent()){
            OrganizationModel org = organization.get();
            org.setIs_proved(true);

            // Save the updated organization
            organizationRepository.save(org);

            return ResponseEntity.ok("Organization approved successfully");
        }
        else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/organizations/{id}")
    //ROLE_ORGANIZATION ROLE_ADMIN
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

    @GetMapping("organizations/search")
   //'ROLE_ORGANIZATIONS','ROLE_ADMIN','ROLE_VOLUNTEER'
    public ResponseEntity<Optional<OrganizationModel>> searchOrganizationsByName(@RequestParam String name) {
        Optional<OrganizationModel>  foundOrganizations = organizationRepository.findByName(name);
        return ResponseEntity.ok(foundOrganizations);
    }

    @PutMapping("/organizations/{id}")
    //'ROLE_ORGANIZATION', 'ROLE_ADMIN'
    public ResponseEntity<String> updateOrganization(@PathVariable Long id, @RequestBody OrganizationModel updatedOrganization) {
        try{

            Optional<OrganizationModel> existingOrganization = organizationRepository.findById(id);
            if (existingOrganization.isPresent()) {

                OrganizationModel organizationUpdated = existingOrganization.get();

                String loggedInUserEmail =organizationUpdated.getEmail();

                if (!loggedInUserEmail.equals(updatedOrganization.getEmail())) {
                    return ResponseEntity.badRequest().body("You can only get your own profile's info");
                }

                if (updatedOrganization.getName() != null) {
                    organizationUpdated.setName(updatedOrganization.getName());
                }
                if (updatedOrganization.getDescription() != null) {
                    organizationUpdated.setDescription(updatedOrganization.getDescription());
                }
                if (updatedOrganization.getPassword() != null) {
                    organizationUpdated.setPassword(updatedOrganization.getPassword());
                }
                if (updatedOrganization.getEmail() != null) {
                    organizationUpdated.setEmail(updatedOrganization.getEmail());
                }
                organizationRepository.save(organizationUpdated);
                return ResponseEntity.ok("Organization updated successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        }catch(Exception e){
            return ResponseEntity.ofNullable(e.getMessage());
        }
    }
}
