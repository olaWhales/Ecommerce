package com.semicolon.ecommerceTask.infrastructure.output.persistence.repositories;

import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.persistenceEntities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ManageProductRepository extends JpaRepository<ProductEntity, UUID>{
    List<ProductEntity> findAllBySellerId(String sellerId);

    Optional<ProductEntity> findByIdAndSellerId(UUID productId, String sellerId);
}