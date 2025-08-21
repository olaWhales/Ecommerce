package com.semicolon.ecommerceTask.domain.services;

import com.semicolon.ecommerceTask.application.port.input.CartItemUseCase;
import com.semicolon.ecommerceTask.domain.model.CartDomainObject;

import java.util.Optional;
import java.util.UUID;

public class CartItemService implements CartItemUseCase {
    @Override
    public CartDomainObject save(CartDomainObject cart) {
        return null;
    }

    @Override
    public Optional<CartDomainObject> findById(UUID id) {
        return Optional.empty();
    }
}
