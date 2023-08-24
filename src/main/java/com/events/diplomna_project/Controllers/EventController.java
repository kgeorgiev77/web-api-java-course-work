package com.events.diplomna_project.Controllers;

import com.events.diplomna_project.Models.EventModel;
import com.events.diplomna_project.Models.UserModel;
import com.events.diplomna_project.Repositories.EventRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class EventController {
    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepo) {
        eventRepository = eventRepo;
    }

    @GetMapping("/events")
    @PreAuthorize("hasAnyAuthority('ROLE_ORGANIZATION','ROLE_HOST', 'ROLE_ADMIN')")
    public List<EventModel> getAllEvents(){
        return eventRepository.findAll();
    }

    @PostMapping("/events")
    @PreAuthorize("hasAnyAuthority('ROLE_HOST ','ROLE_ADMIN')")
    public ResponseEntity<String> createEvent(@RequestBody EventModel event){
        try {
            eventRepository.save(event);
            return ResponseEntity.ok("Event created successfully");
        }catch (Exception e){
            return  ResponseEntity.ofNullable(e.getMessage());
        }
    }
    @GetMapping("events/search")
    @PreAuthorize("hasAnyAuthority('ROLE_ORGANIZATIONS','ROLE_ADMIN','ROLE_VOLUNTEER')")
    public ResponseEntity<Optional<EventModel>> searchEventsByName(@RequestParam String name) {
        Optional<EventModel> foundEvents = eventRepository.findByName(name);
        return ResponseEntity.ok(foundEvents);
    }
    @GetMapping("/events/{eventId}/volunteers")
    @PreAuthorize("hasAnyAuthority('ROLE_ORGANIZATION','ROLE_HOST', 'ROLE_ADMIN')")
    public ResponseEntity<List<UserModel>> getEventVolunteers(@PathVariable Long eventId) {
        Optional<EventModel> optionalEvent = eventRepository.findById(eventId);

        if (optionalEvent.isPresent()) {
            EventModel event = optionalEvent.get();
            List<UserModel> volunteers = event.getVolunteers();
            return ResponseEntity.ok(volunteers);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/events/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_HOST ','ROLE_ADMIN')")
    public ResponseEntity<String> deleteOrganization(@PathVariable Long id) {
        try{
            if (eventRepository.existsById(id)) {
                eventRepository.deleteById(id);
                return ResponseEntity.ok("Event deleted successfully");
            }
            else{
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e){
            return ResponseEntity.ofNullable(e.getMessage());
        }
    }

    @PutMapping("/events/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_HOST ','ROLE_ADMIN')")
    public ResponseEntity<String> updateEvent(@PathVariable Long id, @RequestBody EventModel updatedEvent) {
        try{
            Optional<EventModel> existingEvent = eventRepository.findById(id);
            if (existingEvent.isPresent()) {
                EventModel eventUpdated = existingEvent.get();

                if (updatedEvent.getName() != null) {
                    eventUpdated.setName(updatedEvent.getName());
                }
                if (updatedEvent.getDescription() != null) {
                    eventUpdated.setDescription(updatedEvent.getDescription());
                }
                if (updatedEvent.getDate() != null) {
                    eventUpdated.setDate(updatedEvent.getDate());
                }
                if (updatedEvent.getDonation() != null) {
                    eventUpdated.setDonation(updatedEvent.getDonation());
                }
                if (updatedEvent.getGoal() != null) {
                    eventUpdated.setGoal(updatedEvent.getGoal());
                }
                if (updatedEvent.getBadges() != null) {
                    eventUpdated.setBadges(updatedEvent.getBadges());
                }
                eventRepository.save(eventUpdated);

                return ResponseEntity.ok("Event updated successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        }catch(Exception e){
            return ResponseEntity.ofNullable(e.getMessage());
        }
    }
}

