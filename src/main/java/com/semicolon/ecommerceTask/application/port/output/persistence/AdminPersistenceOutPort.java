package com.semicolon.ecommerceTask.application.port.output.persistence;

import com.semicolon.ecommerceTask.domain.model.AdminDomainObject;
import com.semicolon.ecommerceTask.domain.model.PendingRegistration;
import com.semicolon.ecommerceTask.domain.service.CreateAdminService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AdminPersistenceOutPort {
    boolean existsByEmail(String email);
    void savePendingRegistration(String email, String token, LocalDateTime expiration);
    Optional<PendingRegistration> findPendingTokenByEmail(String email); // Updated to use top-level PendingRegistration    void saveAdmin(AdminDomainObject admin);

    void saveAdmin(AdminDomainObject admin);

    void deletePendingRegistration(String email);
    Optional<AdminDomainObject> findByEmail(String email);
    void deleteAdmin(String email);
    List<AdminDomainObject> findAllAdmins();
}
