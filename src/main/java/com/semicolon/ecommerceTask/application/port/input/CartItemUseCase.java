package com.semicolon.ecommerceTask.application.port.input;

import com.semicolon.ecommerceTask.domain.model.CartDomainObject;

import java.util.Optional;
import java.util.UUID;

public interface CartItemUseCase {
    CartDomainObject save (CartDomainObject cart);
    Optional<CartDomainObject> findById(UUID id);
}
