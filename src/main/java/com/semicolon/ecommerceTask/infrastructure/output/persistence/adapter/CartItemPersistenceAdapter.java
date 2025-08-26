package com.semicolon.ecommerceTask.infrastructure.output.persistence.adapter;

import com.semicolon.ecommerceTask.application.port.output.persistence.CartItemPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.CartItemDomainObject;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.persistenceEntities.CartItemEntity;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.mapper.CartItemMapper;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.repositories.CartItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class CartItemPersistenceAdapter implements CartItemPersistenceOutPort {
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;

    @Override
    public CartItemDomainObject save(CartItemDomainObject cartItem) {
        CartItemEntity entity = cartItemMapper.toEntity(cartItem);
        CartItemEntity savedEntity = cartItemRepository.save(entity);
        return cartItemMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<CartItemDomainObject> findById(UUID id) {
        return cartItemRepository.findById(id).map(cartItemMapper::toDomain);
    }

    @Override
    public List<CartItemDomainObject> findByCartId(UUID cartId) {
        List<CartItemEntity> entities = cartItemRepository.findByCartEntityId(cartId);
        return entities.stream().map(cartItemMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<CartItemDomainObject> findByCartIdAndProductId(UUID cartId, UUID productId) {
        return cartItemRepository.findByIdAndProductEntityId(cartId, productId).map(cartItemMapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        cartItemRepository.deleteById(id);
    }

    @Override
    public Optional<CartItemDomainObject> findByProductIdAndUserId(UUID productId, String userId) {
        return cartItemRepository.findByProductEntityIdAndCartEntityUserId(productId, userId)
                .map(cartItemMapper::toDomain);    }

    @Override
    public Optional<CartItemEntity> findByIdAndCartEntityUserId(UUID cartItemId, String userId) {
        return cartItemRepository.findByIdAndCartEntityUserId(cartItemId, userId);
    }
}

