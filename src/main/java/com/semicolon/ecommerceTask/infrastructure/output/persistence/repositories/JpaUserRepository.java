package com.semicolon.ecommerceTask.infrastructure.output.persistence.repositories;

import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.persistenceEntities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, String> {
    boolean existsByEmail(String email);
    Optional<UserEntity> findByEmail(String email);
    void deleteByEmail(String email);
}

