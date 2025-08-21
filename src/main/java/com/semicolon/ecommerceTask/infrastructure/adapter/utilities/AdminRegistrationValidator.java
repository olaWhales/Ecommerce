package com.semicolon.ecommerceTask.infrastructure.adapter.utilities;

import com.semicolon.ecommerceTask.application.port.output.KeycloakAdminOutPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.AdminPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.exception.AdminNotFoundException;
import com.semicolon.ecommerceTask.domain.model.PendingRegistrationDomainObject;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entities.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AdminRegistrationValidator {
    private final AdminPersistenceOutPort adminPersistenceOutPort;
    private final KeycloakAdminOutPort keycloakAdminOutPort;

    private static final long REGISTRATION_TOKEN_VALIDITY_HOURS = 24;

    public void ensureAdminDoesNotExist(String email) {
        if (adminPersistenceOutPort.existsByEmail(email)) {throw new AdminNotFoundException(MessageUtil.ADMIN_ALREADY_EXISTS.formatted(email));}
        Optional<UserDomainObject> existingUser = keycloakAdminOutPort.findUserByEmail(email);
        if (existingUser.isPresent()) {throw new AdminNotFoundException(MessageUtil.ADMIN_ALREADY_EXISTS_IN_KEYCLOAK.formatted(email));
        }
    }

    public PendingRegistrationDomainObject getValidPendingRegistrationOrThrow(String email) {
        Optional<PendingRegistrationDomainObject> pendingOptional =
                adminPersistenceOutPort.findPendingRegistrationByEmail(email);
        if (pendingOptional.isEmpty()) {throw new AdminNotFoundException(MessageUtil.NO_PENDING_REGISTRATION.formatted(email));}
        PendingRegistrationDomainObject pending = pendingOptional.get();
        if (LocalDateTime.now().isAfter(pending.getExpiration())) {
            adminPersistenceOutPort.deletePendingRegistration(email);
            throw new AdminNotFoundException(MessageUtil.TOKEN_EXPIRED);}
        return pending;
    }

    public String newRegistrationToken() {return UUID.randomUUID().toString();}
    public LocalDateTime computeExpiration() {return LocalDateTime.now().plusHours(REGISTRATION_TOKEN_VALIDITY_HOURS);}
    public void assignAdminRole(String keycloakId) {
        keycloakAdminOutPort.assignRealmRoles(keycloakId,
                Collections.singletonList(UserRole.ADMIN)
        );
    }
}

