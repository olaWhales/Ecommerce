package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.repository;

import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.PendingAdminRegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PendingRegistrationRepository extends JpaRepository<PendingAdminRegistrationEntity, UUID> {
    Optional<PendingAdminRegistrationEntity> findByEmail(String email);
    void deleteByEmail(String email);
}

