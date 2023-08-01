package com.events.diplomna_project.Controllers;

import com.events.diplomna_project.Models.EventModel;
import com.events.diplomna_project.Repositories.EventRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepo) {
        eventRepository = eventRepo;
    }

    @GetMapping
    public List<EventModel> getAllEvents(){
        return eventRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<String> createEvent(@RequestBody EventModel event){
        try {
            eventRepository.save(event);
            return ResponseEntity.ok("Event created successfully");
        }catch (Exception e){
            return  ResponseEntity.ofNullable(e.getMessage());
        }
    }

    @DeleteMapping("/events/{id}")
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
    public ResponseEntity<String> updateEvent(@PathVariable Long id, @RequestBody EventModel updatedEvent) {
        try{
            Optional<EventModel> existingEvent = eventRepository.findById(id);
            if (existingEvent.isPresent()) {
                EventModel eventUpdated = existingEvent.get();
                eventUpdated.setName(updatedEvent.getName());
                eventUpdated.setDescription(updatedEvent.getDescription());
                eventUpdated.setDate(updatedEvent.getDate());
                eventUpdated.setDonation(updatedEvent.getDonation());
                eventUpdated.setGoal(updatedEvent.getGoal());
                eventUpdated.setBadges(updatedEvent.getBadges());
                return ResponseEntity.ok("Event updated successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        }catch(Exception e){
            return ResponseEntity.ofNullable(e.getMessage());
        }
    }
}

