package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.repository;

import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByKeycloakId(String keycloakId);
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}

