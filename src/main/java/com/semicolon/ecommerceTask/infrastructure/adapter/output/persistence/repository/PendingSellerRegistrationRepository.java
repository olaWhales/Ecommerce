package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.repository;

import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.PendingSellerRegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PendingSellerRegistrationRepository extends JpaRepository<PendingSellerRegistrationEntity, UUID> {
    Optional<PendingSellerRegistrationEntity> findByCustomerEmail(String customerEmail);
}