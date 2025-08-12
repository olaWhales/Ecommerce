package com.semicolon.ecommerceTask.application.port.output.persistence;

import com.semicolon.ecommerceTask.domain.model.AdminDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.AdminEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AdminPersistenceOutPort {
    boolean existsByEmail(String email);
    AdminDomainObject saveAdmin(AdminDomainObject admin);
    void deleteAdmin(String email);
    Optional<AdminDomainObject> findByEmail(String email);
    List<AdminDomainObject> findAllAdmins();
    // New and updated methods for pending registrations
    void createPendingRegistration(String email, String token, LocalDateTime expiration);
    Optional<AdminEntity.PendingRegistrationDomainObject> findPendingRegistrationByEmail(String email);
    void updatePendingRegistration(String email, String token, LocalDateTime expiration);
    void deletePendingRegistration(String email); // Re-added this method

}