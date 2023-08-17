package com.events.diplomna_project.Repositories;

import com.events.diplomna_project.Models.OrganizationModel;
import com.events.diplomna_project.Models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<OrganizationModel, Long> {
    Optional<OrganizationModel> findByName(String username);
}
