package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence;

import com.semicolon.ecommerceTask.application.port.output.persistence.CartPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.CartDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entities.CartEntity;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entities.CartStatus;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.CartMapper;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.repositories.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class CartPersistenceAdapter implements CartPersistenceOutPort {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    @Override
    public CartDomainObject save(CartDomainObject cart) {
        CartEntity entity = cartMapper.toEntity(cart);
        CartEntity savedEntity = cartRepository.save(entity);
        return cartMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<CartDomainObject> findById(UUID id) {
       return cartRepository.findById(id).map(cartMapper::toDomain);
    }

    @Override
    public Optional<CartDomainObject> findByUserIdAndStatus(String userId, CartStatus cartStatus) {
        return cartRepository.findByUserIdAndStatus(userId,cartStatus).stream().findFirst().map(cartMapper::toDomain);
    }
}
