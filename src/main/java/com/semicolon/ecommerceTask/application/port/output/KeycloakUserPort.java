package com.semicolon.ecommerceTask.application.port.output;

import com.semicolon.ecommerceTask.infrastructure.adapter.output.superAdmin.KeycloakUserDto;

public interface KeycloakSuperAdminClient {
    void createUserWithRole(KeycloakUserDto userDto);
}
