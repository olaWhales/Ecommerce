package com.semicolon.ecommerceTask.infrastructure.adapter.output.keycloack;

import com.semicolon.ecommerceTask.application.port.output.KeycloakUserPort;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.configuration.keyCloakProperties.KeycloakAdminProperties;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.enumPackage.UserRole;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil.*;

@Service
@RequiredArgsConstructor
//@Slf4j
public class KeycloakUserAdapter implements KeycloakUserPort {

    private static final Logger logger = LoggerFactory.getLogger(KeycloakUserAdapter.class);

    private final Keycloak keycloak;
    private final KeycloakAdminProperties properties;

    @Override
    public String createUser(UserDomainObject userDomainObject) {
//        logger.info("Keycloak properties - Realm: {}, ClientId: {}, ClientSecret: {}",
//                properties.getRealm(), properties.getClientId(), properties.getClientSecret());
//        logger.info("Creating user with email: {}", userDomainObject.getEmail());

        if (userDomainObject.getEmail() == null || userDomainObject.getPassword() == null) {
            throw new IllegalArgumentException(USER_DATA_CANNOT_BE_NULL);
        }

        // Check if user already exists by email
        List<UserRepresentation> existingUsers = keycloak.realm(properties.getRealm()).users()
                .search(userDomainObject.getEmail(), null, null, null, 0, 1);
        if (!existingUsers.isEmpty()) {
            String existingUserId = existingUsers.get(0).getId();
//            logger.info("User or email {} already exists in Keycloak with ID: {}",
//                    userDomainObject.getEmail(), existingUserId);
            assignRoles(existingUserId, userDomainObject.getRoles());
            return existingUserId; // Return existing user ID
        }

        UserRepresentation userRepresentation = new UserRepresentation();
        // Set username as a combination or use email
        userRepresentation.setUsername(userDomainObject.getEmail()); // Using email as username
        userRepresentation.setEmail(userDomainObject.getEmail());
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(false); // Require email verification

        // Set firstName and lastName as attributes
        Map<String, List<String>> attributes = new HashMap<>();
        if (userDomainObject.getFirstName() != null) {
            attributes.put(FIRSTNAME, Collections.singletonList(userDomainObject.getFirstName()));
        }
        if (userDomainObject.getLastName() != null) {
            attributes.put(LASTNAME, Collections.singletonList(userDomainObject.getLastName()));
        }
        userRepresentation.setAttributes(attributes);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(userDomainObject.getPassword());
        credential.setTemporary(false);
        userRepresentation.setCredentials(Collections.singletonList(credential));

        try (Response response = keycloak.realm(properties.getRealm()).users().create(userRepresentation)) {
            if (response.getStatus() == 409) {
//                logger.info("User or email {} already exists in Keycloak", userDomainObject.getEmail());
                return USER_ALREADY_EXIST_IN_KEYCLOAK;
            } else if (response.getStatus() != 201) {
//                logger.error("Keycloak user creation failed: Status {}, Info: {}", response.getStatus(), response.getStatusInfo());
                throw new IllegalArgumentException(KEYCLOAK_USER_CREATION_FAILED + response.getStatusInfo());
            }

            String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
            assignRoles(userId, userDomainObject.getRoles());
            return userId;
        } catch (Exception e) {
            logger.error(EXCEPTION_DURING_USER_CREATION, e);
            throw e;
        }
    }

    private void assignRoles(String userId, List<UserRole> roles) {
        if (roles != null) {
            roles.forEach(role -> {
                try {
                    RoleRepresentation roleRepresentation = keycloak.realm(properties.getRealm())
                            .roles().get(role.name()).toRepresentation();
                    keycloak.realm(properties.getRealm()).users().get(userId).roles()
                            .realmLevel().add(Collections.singletonList(roleRepresentation));
                } catch (Exception e) {
                    logger.warn(FAILED_TO_ASSIGN_ROLE_USER, role.name(), userId, e.getMessage());
                }
            });
        }
    }
}