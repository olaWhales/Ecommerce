package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.repositories;

import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entities.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, UUID> {

}
