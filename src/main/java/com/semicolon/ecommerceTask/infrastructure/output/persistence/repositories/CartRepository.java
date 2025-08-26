package com.semicolon.ecommerceTask.infrastructure.output.persistence.repositories;

import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.persistenceEntities.CartEntity;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.enumPackages.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, UUID> {
    Optional<CartEntity> findByUserIdAndStatus(String userId, CartStatus cartStatus);
}
