package com.semicolon.ecommerceTask.application.port.input;

import com.semicolon.ecommerceTask.domain.model.PendingSellerRegistration;

import java.util.List;
import java.util.Optional;

public interface PendingSellerRegistrationOutPort {
    void savePendingRegistration(PendingSellerRegistration registration);
    Optional<PendingSellerRegistration> findByEmail(String email);
    Optional<PendingSellerRegistration> findById(Long id);
    List<PendingSellerRegistration> findAllPendingRegistrations();
    void deletePendingRegistration(String email);
}