package com.semicolon.ecommerceTask.application.port.output.persistence;

import com.semicolon.ecommerceTask.domain.model.SellerFormSubmissionDomain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SellerFormSubmissionPersistenceOutPort {
    Optional<SellerFormSubmissionDomain> findByKeycloakUserId(String keycloakUserId);
    SellerFormSubmissionDomain savePendingRegistration(SellerFormSubmissionDomain registration);
    Optional<SellerFormSubmissionDomain> findByEmail(String email);
    Optional<SellerFormSubmissionDomain> findById(UUID id);
    SellerFormSubmissionDomain save(SellerFormSubmissionDomain submission);
    List<SellerFormSubmissionDomain> findAllPendingRegistrations();
    void deletePendingRegistration(String email);
}

