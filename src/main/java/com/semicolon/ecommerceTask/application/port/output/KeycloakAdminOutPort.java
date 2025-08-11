package com.semicolon.ecommerceTask.application.port.output;

import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import java.util.List;
import java.util.Optional;

public interface KeycloakAdminOutPort {
    String createUser(UserDomainObject user, String password);
    void deleteUser(String keycloakId);
    Optional<String> findUserByEmail(String email);
    void assignRealmRoles(String keycloakId, List<String> roleNames);
    void removeRealmRole(String keycloakId, String roleName);
}