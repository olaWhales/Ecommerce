package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.repository;

import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.PendingRegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PendingRegistrationRepository extends JpaRepository<PendingRegistrationEntity, String> {
    Optional<PendingRegistrationEntity> findByEmail(String email);
    void deleteByEmail(String email);
}

