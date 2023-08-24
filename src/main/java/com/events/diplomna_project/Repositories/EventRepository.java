package com.events.diplomna_project.Repositories;

import com.events.diplomna_project.Models.EventModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<EventModel, Long> {
    Optional<EventModel> findByName(String name);
}
