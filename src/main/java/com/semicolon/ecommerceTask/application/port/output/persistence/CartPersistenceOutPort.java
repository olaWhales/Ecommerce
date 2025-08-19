package com.semicolon.ecommerceTask.application.port.output.persistence;

import com.semicolon.ecommerceTask.domain.model.CartDomainObject;

import java.util.Optional;
import java.util.UUID;

public interface CartPersistenceOutPort {
    CartDomainObject save (CartDomainObject cart);
    Optional<CartDomainObject> findById (UUID id);
}
