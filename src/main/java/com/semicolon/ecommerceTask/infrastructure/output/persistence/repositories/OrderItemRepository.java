package com.semicolon.ecommerceTask.infrastructure.output.persistence.repositories;

import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.persistenceEntities.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, UUID> {

}

