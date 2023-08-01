package com.events.diplomna_project.Repositories;

import com.events.diplomna_project.Models.OrganizationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<OrganizationModel, Long> {
}
