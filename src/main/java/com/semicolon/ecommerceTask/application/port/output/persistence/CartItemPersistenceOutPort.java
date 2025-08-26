package com.semicolon.ecommerceTask.application.port.output.persistence;

import com.semicolon.ecommerceTask.domain.model.CartItemDomainObject;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.persistenceEntities.CartItemEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartItemPersistenceOutPort {
    CartItemDomainObject save (CartItemDomainObject cartItem);
    Optional<CartItemDomainObject> findById (UUID id);
    List<CartItemDomainObject> findByCartId(UUID cartId);
    Optional<CartItemDomainObject> findByCartIdAndProductId(UUID cartId, UUID productId);
    void deleteById(UUID id);

    Optional<CartItemDomainObject> findByProductIdAndUserId(UUID productId, String userId);

    Optional<CartItemEntity> findByIdAndCartEntityUserId(UUID cartItemId, String userId);
}
