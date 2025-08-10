package com.semicolon.ecommerceTask.application.port.output;

import com.semicolon.ecommerceTask.domain.model.AdminDomainObject;

import java.util.Optional;

public interface KeycloakAdminOutPort {
    String createUser(AdminDomainObject admin, String password);
    void deleteUser(String keycloakId);

    Optional<String> findUserByEmail(String adminEmail);
    // KeycloakAdminOutPort.java
// ...
    void assignRealmRole(String keycloakId, String roleName);
}