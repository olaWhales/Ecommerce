package com.semicolon.ecommerceTask.application.port.output.persistence;

import com.semicolon.ecommerceTask.domain.model.CartItemDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entities.CartItemEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartItemPersistenceOutPort {
    CartItemDomainObject save (CartItemDomainObject cartItem);
    Optional<CartItemDomainObject> findById (UUID id);
    List<CartItemDomainObject> findByCartId(UUID cartId);
    Optional<CartItemDomainObject> findByCartIdAndProductId(UUID cartId, UUID productId);
    void deleteById(UUID id);
}
