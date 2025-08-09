package com.semicolon.ecommerceTask.application.port.output;

import com.semicolon.ecommerceTask.domain.model.AdminDomainObject;

public interface KeycloakAdminOutPort {
    String createUser(AdminDomainObject admin);
    void deleteUser(String keycloakId);
}