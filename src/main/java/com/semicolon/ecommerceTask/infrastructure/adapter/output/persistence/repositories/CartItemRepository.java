package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.repositories;

import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entities.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, UUID> {

    List<CartItemEntity> findByCartEntityId(UUID cartEntityId);
    Optional<CartItemEntity> findByIdAndProductEntityId(UUID id, UUID productEntityId);
}