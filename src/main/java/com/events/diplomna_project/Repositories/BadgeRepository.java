package com.events.diplomna_project.Repositories;

import com.events.diplomna_project.Models.BadgeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadgeRepository extends JpaRepository<BadgeModel, Long> {
}
