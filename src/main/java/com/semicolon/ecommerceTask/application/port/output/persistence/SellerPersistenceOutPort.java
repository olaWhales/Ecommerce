package com.semicolon.ecommerceTask.application.port.output.persistence;

import com.semicolon.ecommerceTask.domain.model.SellerDomainObject;

import java.util.Optional;

public interface SellerPersistenceOutPort {
    void saveSeller(SellerDomainObject seller);
    Optional<SellerDomainObject> findByEmail(String email);
    // You can add more methods here as needed
}