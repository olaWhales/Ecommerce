package com.semicolon.ecommerceTask.domain.service;

import com.semicolon.ecommerceTask.application.port.output.KeycloakAdminOutPort;
import com.semicolon.ecommerceTask.domain.exception.AdminException;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil.KEYCLOAK_CREATION_FAILED;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    private final KeycloakAdminOutPort keycloakAdminOutPort;

    @Transactional
    // This is the original method signature, kept for compatibility
    public String registerUserInKeycloak(UserDomainObject user, String password) {
        if (keycloakAdminOutPort.findUserByEmail(user.getEmail()).isPresent()) {
            throw new AdminException(MessageUtil.ADMIN_ALREADY_EXISTS_IN_KEYCLOAK.formatted(user.getEmail()));
        }

        String keycloakId = keycloakAdminOutPort.createUser(user, password);
        if (keycloakId == null) {
            throw new AdminException(KEYCLOAK_CREATION_FAILED);
        }

        return keycloakId;
    }

    @Transactional
    // This is the new method for ApproveSellerService, which returns the full object
    public UserDomainObject registerUserInKeycloakAndReturnObject(UserDomainObject user, String password) {
        if (keycloakAdminOutPort.findUserByEmail(user.getEmail()).isPresent()) {
            throw new AdminException(MessageUtil.ADMIN_ALREADY_EXISTS_IN_KEYCLOAK.formatted(user.getEmail()));
        }
        String keycloakId = keycloakAdminOutPort.createUser(user, password);
        if (keycloakId == null) {
            throw new AdminException(KEYCLOAK_CREATION_FAILED);
        }
        return user.toBuilder()
                .keycloakId(keycloakId)
                .build();
    }
}