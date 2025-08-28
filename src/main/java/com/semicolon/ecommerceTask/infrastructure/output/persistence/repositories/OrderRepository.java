package com.semicolon.ecommerceTask.infrastructure.output.persistence.repositories;

import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.persistenceEntities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity , UUID> {

}
