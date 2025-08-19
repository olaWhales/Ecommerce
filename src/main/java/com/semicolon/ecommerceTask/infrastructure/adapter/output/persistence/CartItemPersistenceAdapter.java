package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence;

import com.semicolon.ecommerceTask.application.port.output.persistence.CartItemPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.CartItemDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.repositories.CartItemRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class CartItemPersistenceAdapter implements CartItemPersistenceOutPort {
    private final CartItemRepository cartItemRepository;

    @Override
    public CartItemDomainObject save(CartItemDomainObject cartItem) {
        return null;
    }

    @Override
    public Optional<CartItemDomainObject> findById(UUID id) {
        return Optional.empty();
    }
}
