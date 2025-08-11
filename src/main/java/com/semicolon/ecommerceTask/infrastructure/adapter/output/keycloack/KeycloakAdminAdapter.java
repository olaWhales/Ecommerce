package com.semicolon.ecommerceTask.infrastructure.adapter.output.keycloack;

import com.semicolon.ecommerceTask.application.port.output.KeycloakAdminOutPort;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class KeycloakAdminAdapter implements KeycloakAdminOutPort {

    private final Keycloak keycloakAdminClient;

    @Value("${keycloak.admin.realm}")
    private String realm;

    @Override
    public String createUser(UserDomainObject user, String password) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(user.getEmail());
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setFirstName(user.getFirstName());
        userRepresentation.setLastName(user.getLastName());
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(true);

        Response response = keycloakAdminClient.realm(realm).users().create(userRepresentation);
        String keycloakId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

        // Set password
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(password);
        passwordCred.setTemporary(false);

        keycloakAdminClient.realm(realm).users().get(keycloakId).resetPassword(passwordCred);

        return keycloakId;
    }

    @Override
    public void deleteUser(String keycloakId) {
        keycloakAdminClient.realm(realm).users().delete(keycloakId);
    }

//    @Override
//    public Optional<String> findUserByEmail(String email) {
//        List<UserRepresentation> users = keycloakAdminClient.realm(realm).users().searchByEmail(email, true);
//        if (users != null && !users.isEmpty()) {
//            // Find a user with an exact email match, as searchByEmail can return partial matches
//            return users.stream()
//                    .filter(user -> email.equals(user.getEmail()))
//                    .findFirst()
//                    .map(UserRepresentation::getId);
//        }
//        return Optional.empty();
//    }
    @Override
    public Optional<String> findUserByEmail(String email) {
//        log.info("KeycloakAdminAdapter: Searching for user with email '{}' in realm '{}'", email, realm);

        UsersResource usersResource = keycloakAdminClient.realm(realm).users();

        // Use the general search method which is often more reliable
        List<UserRepresentation> users = usersResource.search(email, true);

        if (users != null && !users.isEmpty()) {
//            log.info("KeycloakAdminAdapter: Found {} user(s) matching the email.", users.size());

            // Filter for an exact match
            return users.stream()
                    .filter(user -> email.equalsIgnoreCase(user.getEmail())) // Use equalsIgnoreCase for robustness
                    .findFirst()
                    .map(UserRepresentation::getId);
        }

//    log.warn("KeycloakAdminAdapter: No user found for email '{}'.", email);
    return Optional.empty();
}

    @Override
    public void assignRealmRoles(String keycloakId, List<String> roleNames) {
        List<RoleRepresentation> roles = keycloakAdminClient.realm(realm).roles().list();
        List<RoleRepresentation> realmRoles = roles.stream()
                .filter(role -> roleNames.contains(role.getName()))
                .toList();

        UserResource userResource = keycloakAdminClient.realm(realm).users().get(keycloakId);
        // Corrected API call
        userResource.roles().realmLevel().add(realmRoles);
    }

    @Override
    public void removeRealmRole(String keycloakId, String roleName) {
        RoleRepresentation roleToRemove = keycloakAdminClient.realm(realm).roles().get(roleName).toRepresentation();
        UserResource userResource = keycloakAdminClient.realm(realm).users().get(keycloakId);
        // Corrected API call
        userResource.roles().realmLevel().remove(Collections.singletonList(roleToRemove));
    }
}