package com.semicolon.ecommerceTask.domain.service;

import com.semicolon.ecommerceTask.application.port.input.ApproveSellerUseCase;
import com.semicolon.ecommerceTask.application.port.output.KeycloakAdminOutPort;
import com.semicolon.ecommerceTask.application.port.output.PendingRegistrationOutPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.UserPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.ActionOnSellerApprovalRequest;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.enumPackage.ApprovalAction;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.enumPackage.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminActionOnSellerService implements ApproveSellerUseCase {

    private final PendingRegistrationOutPort pendingRegistrationOutPort;
    private final KeycloakAdminOutPort keycloakAdminOutPort;
    private final UserPersistenceOutPort userPersistenceOutPort;

    @Override
    public UserDomainObject approveSeller(UUID registrationId, ActionOnSellerApprovalRequest request) {
        log.info("Attempting to fetch pending registration with ID: {}", registrationId);
        var pendingRegistration = pendingRegistrationOutPort.findById(registrationId)
                .orElseThrow(() -> new IllegalArgumentException("Pending registration not found with ID: " + registrationId));
        log.info("Fetched pending registration: {}", pendingRegistration);
        // Rest
        if (request.getAction() == ApprovalAction.REJECT) {
            pendingRegistrationOutPort.delete(registrationId);
            return null; // Indicates rejection
        }

        // Prepare user with SELLER role
        List<UserRole> roles = Collections.singletonList(UserRole.SELLER);
        UserDomainObject newUser = UserDomainObject.builder()
                .email(pendingRegistration.getEmail())
                .firstName(pendingRegistration.getFirstName())
                .lastName(pendingRegistration.getLastName())
                .roles(roles)
                .build();

        // Register user in Keycloak (assuming UserRegistrationService is integrated)
        UserDomainObject userFromKeycloak = registerUserInKeycloak(newUser, pendingRegistration.getPassword());
        log.info("=====> User from Keycloak: {}", userFromKeycloak.getKeycloakId());

        // Assign SELLER role in Keycloak
        keycloakAdminOutPort.assignRealmRoles(userFromKeycloak.getKeycloakId(), Collections.singletonList(UserRole.SELLER.name()));

        // Persist the approved user
        UserDomainObject approvedUser = userPersistenceOutPort.save(userFromKeycloak);
        log.info("===> Approved user details: {}", approvedUser.getKeycloakId());

        // Delete the pending registration
        pendingRegistrationOutPort.delete(registrationId);

        return approvedUser;
    }

    // Placeholder method (replace with actual implementation if separate)
    private UserDomainObject registerUserInKeycloak(UserDomainObject user, String password) {
        // This should call UserRegistrationService or KeycloakAdminOutPort.createUser
        throw new UnsupportedOperationException("Implement Keycloak user registration logic");
    }
}