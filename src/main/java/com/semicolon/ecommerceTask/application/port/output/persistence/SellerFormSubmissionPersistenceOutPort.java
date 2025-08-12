package com.semicolon.ecommerceTask.application.port.output.persistence;

import com.semicolon.ecommerceTask.domain.model.SellerFormSubmissionDomain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SellerFormSubmissionPersistenceOutPort {
    SellerFormSubmissionDomain savePendingRegistration(SellerFormSubmissionDomain registration);
    Optional<SellerFormSubmissionDomain> findByEmail(String email);
    Optional<SellerFormSubmissionDomain> findById(UUID id);
    List<SellerFormSubmissionDomain> findAllPendingRegistrations();
    SellerFormSubmissionDomain save(SellerFormSubmissionDomain submission);
    void deletePendingRegistration(String email);
}
