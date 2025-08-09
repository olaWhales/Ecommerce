package com.semicolon.ecommerceTask.infrastructure.adapter.output.repositories;

import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<AdminEntity, UUID> {
    boolean existsByEmail(String email);
    Optional<AdminEntity> findByEmail(String email);
    void deleteByEmail(String email);
}
