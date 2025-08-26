package com.semicolon.ecommerceTask.infrastructure.output.persistence.repositories;

import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.persistenceEntities.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, UUID> {

    List<CartItemEntity> findByCartEntityId(UUID cartEntityId);
    Optional<CartItemEntity> findByIdAndProductEntityId(UUID id, UUID productEntityId);

    Optional<CartItemEntity> findByProductEntityIdAndCartEntityUserId(UUID productId, String userId);
//    Optional<CartItemEntity> findByIdAndCartEntityUser_Id(UUID id, String userId);
    Optional<CartItemEntity> findByIdAndCartEntityUserId(UUID cartItemId, String userId);
}

