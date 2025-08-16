package com.semicolon.ecommerceTask.application.port.output;

import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.enumPackage.UserRole;

import java.util.List;
import java.util.Optional;

public interface KeycloakAdminOutPort {
    String createUser(UserDomainObject user, String password);
    void deleteUser(String keycloakId);
    Optional<UserDomainObject> findUserByEmail(String email);
    void assignRealmRoles(String keycloakId, List<UserRole> roleNames);
    void removeRealmRole(String keycloakId, List<UserRole> roleNames);
}
