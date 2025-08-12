package com.semicolon.ecommerceTask.application.port.input;

import com.semicolon.ecommerceTask.domain.model.SellerFormSubmissionDomain;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.SellerFormSubmissionEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SellerFormSubmissionOutPort {
    SellerFormSubmissionDomain savePendingRegistration(SellerFormSubmissionDomain registration);
    Optional<SellerFormSubmissionDomain> findByEmail(String email);
    Optional<SellerFormSubmissionDomain> findById(UUID id);
    List<SellerFormSubmissionDomain> findAllPendingRegistrations();
    void deletePendingRegistration(String email);
}
