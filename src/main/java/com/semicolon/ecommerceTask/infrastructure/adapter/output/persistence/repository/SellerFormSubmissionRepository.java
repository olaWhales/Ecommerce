package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.repository;

import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.temporaryPendingRegistrationEntity.SellerFormSubmissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SellerFormSubmissionRepository extends JpaRepository<SellerFormSubmissionEntity, UUID> {
    Optional<SellerFormSubmissionEntity> findByKeycloakUserId(String keycloakUserId);
    Optional<SellerFormSubmissionEntity> findByCustomerEmail(String customerEmail);
}