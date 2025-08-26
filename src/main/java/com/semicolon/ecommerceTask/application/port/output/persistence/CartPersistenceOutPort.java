package com.semicolon.ecommerceTask.application.port.output.persistence;

import com.semicolon.ecommerceTask.domain.model.CartDomainObject;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.enumPackages.CartStatus;

import java.util.Optional;
import java.util.UUID;

public interface CartPersistenceOutPort {
    CartDomainObject save (CartDomainObject cart);
    Optional<CartDomainObject> findById (UUID id);
    Optional<CartDomainObject> findByUserIdAndStatus(String userId, CartStatus cartStatus);

//    Optional<CartDomainObject> findByUserId(String userId);
}
