//package com.semicolon.ecommerceTask.infrastructure.adapter.output.keycloack;
//
//import com.semicolon.ecommerceTask.application.port.output.KeycloakUserPort;
//import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
//import com.semicolon.ecommerceTask.infrastructure.adapter.configuration.keyCloakProperties.KeycloakAdminProperties;
//import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserRole;
//import jakarta.ws.rs.core.Response;
//import lombok.RequiredArgsConstructor;
//import org.keycloak.admin.client.Keycloak;
//import org.keycloak.representations.idm.CredentialRepresentation;
//import org.keycloak.representations.idm.RoleRepresentation;
//import org.keycloak.representations.idm.UserRepresentation;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class KeycloakUserAdapter implements KeycloakUserPort {
//
//    private static final Logger logger = LoggerFactory.getLogger(KeycloakUserAdapter.class);
//
//    private final Keycloak keycloak;
//    private final KeycloakAdminProperties properties;
//
//    @Override
//    public String createUser(UserDomainObject userDomainObject) {
////        logger.info("Keycloak properties - Realm: {}, ClientId: {}, ClientSecret: {}",
////                properties.getRealm(), properties.getClientId(), properties.getClientSecret());
////        logger.info("Creating user with email: {}", userDomainObject.getEmail());
//
//        if (userDomainObject.getEmail() == null || userDomainObject.getPassword() == null) {
//            throw new IllegalArgumentException("User data cannot be null");
//        }
//
//        // Check if user already exists by email
//        List<UserRepresentation> existingUsers = keycloak.realm(properties.getRealm()).users()
//                .search(userDomainObject.getEmail(), null, null, null, 0, 1);
//        if (!existingUsers.isEmpty()) {
//            String existingUserId = existingUsers.get(0).getId();
////            logger.info("User or email {} already exists in Keycloak with ID: {}",
////                    userDomainObject.getEmail(), existingUserId);
//            assignRoles(existingUserId, userDomainObject.getRoles());
//            return existingUserId; // Return existing user ID
//        }
//
//        UserRepresentation userRepresentation = new UserRepresentation();
//        userRepresentation.setUsername(userDomainObject.getFirstName());
//        userRepresentation.setUsername(userDomainObject.getLastName());
//        userRepresentation.setEmail(userDomainObject.getEmail());
//        userRepresentation.setEnabled(true);
//        userRepresentation.setEmailVerified(false); // Require email verification
//
//        CredentialRepresentation credential = new CredentialRepresentation();
//        credential.setType(CredentialRepresentation.PASSWORD);
//        credential.setValue(userDomainObject.getPassword());
//        credential.setTemporary(false);
//        userRepresentation.setCredentials(Collections.singletonList(credential));
//
//        try (Response response = keycloak.realm(properties.getRealm()).users().create(userRepresentation)) {
//            if (response.getStatus() == 409) { // Conflict
////                logger.info("User or email {} already exists in Keycloak", userDomainObject.getEmail());
//                return null; // Indicate conflict without throwing an exception
//            } else if (response.getStatus() != 201) {
////                logger.error("Keycloak user creation failed: Status {}, Info: {}", response.getStatus(), response.getStatusInfo());
//                throw new RuntimeException("Keycloak user creation failed: " + response.getStatusInfo());
//            }
//
//            String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
//            assignRoles(userId, userDomainObject.getRoles());
////            logger.info("User created in Keycloak with ID: {}", userId);
//            return userId;
//        } catch (Exception e) {
////            logger.error("Exception during user creation", e);
//            throw e;
//        }
//    }
//
//    private void assignRoles(String userId, List<UserRole> roles) {
//        if (roles != null) {
//            roles.forEach(role -> {
//                try {
//                    RoleRepresentation roleRepresentation = keycloak.realm(properties.getRealm())
//                            .roles().get(role.name()).toRepresentation();
//                    keycloak.realm(properties.getRealm()).users().get(userId).roles()
//                            .realmLevel().add(Collections.singletonList(roleRepresentation));
//                    logger.info("Assigned role {} to user {}", role.name(), userId);
//                } catch (Exception e) {
//                    logger.warn("Failed to assign role {} to user {}: {}", role.name(), userId, e.getMessage());
//                }
//            });
//        }
//    }
//}
package com.semicolon.ecommerceTask.infrastructure.adapter.output.keycloack;

import com.semicolon.ecommerceTask.application.port.output.KeycloakUserPort;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.configuration.keyCloakProperties.KeycloakAdminProperties;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserRole;
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

@Service
@RequiredArgsConstructor
public class KeycloakUserAdapter implements KeycloakUserPort {

    private static final Logger logger = LoggerFactory.getLogger(KeycloakUserAdapter.class);

    private final Keycloak keycloak;
    private final KeycloakAdminProperties properties;

    @Override
    public String createUser(UserDomainObject userDomainObject) {
        logger.info("Keycloak properties - Realm: {}, ClientId: {}, ClientSecret: {}",
                properties.getRealm(), properties.getClientId(), properties.getClientSecret());
        logger.info("Creating user with email: {}", userDomainObject.getEmail());

        if (userDomainObject.getEmail() == null || userDomainObject.getPassword() == null) {
            throw new IllegalArgumentException("User data cannot be null");
        }

        // Check if user already exists by email
        List<UserRepresentation> existingUsers = keycloak.realm(properties.getRealm()).users()
                .search(userDomainObject.getEmail(), null, null, null, 0, 1);
        if (!existingUsers.isEmpty()) {
            String existingUserId = existingUsers.get(0).getId();
            logger.info("User or email {} already exists in Keycloak with ID: {}",
                    userDomainObject.getEmail(), existingUserId);
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
            attributes.put("firstName", Collections.singletonList(userDomainObject.getFirstName()));
        }
        if (userDomainObject.getLastName() != null) {
            attributes.put("lastName", Collections.singletonList(userDomainObject.getLastName()));
        }
        userRepresentation.setAttributes(attributes);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(userDomainObject.getPassword());
        credential.setTemporary(false);
        userRepresentation.setCredentials(Collections.singletonList(credential));

        try (Response response = keycloak.realm(properties.getRealm()).users().create(userRepresentation)) {
            if (response.getStatus() == 409) { // Conflict
                logger.info("User or email {} already exists in Keycloak", userDomainObject.getEmail());
                return null; // Indicate conflict without throwing an exception
            } else if (response.getStatus() != 201) {
                logger.error("Keycloak user creation failed: Status {}, Info: {}", response.getStatus(), response.getStatusInfo());
                throw new RuntimeException("Keycloak user creation failed: " + response.getStatusInfo());
            }

            String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
            assignRoles(userId, userDomainObject.getRoles());
            logger.info("User created in Keycloak with ID: {}", userId);
            return userId;
        } catch (Exception e) {
            logger.error("Exception during user creation", e);
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
                    logger.info("Assigned role {} to user {}", role.name(), userId);
                } catch (Exception e) {
                    logger.warn("Failed to assign role {} to user {}: {}", role.name(), userId, e.getMessage());
                }
            });
        }
    }
}