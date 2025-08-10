package com.semicolon.ecommerceTask.infrastructure.adapter.output.keycloack;

import com.semicolon.ecommerceTask.application.port.output.KeycloakAdminOutPort;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class KeycloakAdminAdapter implements KeycloakAdminOutPort {

    private final Keycloak keycloak;
    @Value("${keycloak.realm}")
    private String realm;

    public KeycloakAdminAdapter(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    @Override
    public String createUser(UserDomainObject user, String password) {
        UserRepresentation userRep = new UserRepresentation();
        userRep.setEmail(user.getEmail());
        userRep.setUsername(user.getEmail());
        userRep.setFirstName(user.getFirstName());
        userRep.setLastName(user.getLastName());
        userRep.setEnabled(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);
        userRep.setCredentials(Collections.singletonList(credential));

        try (Response response = keycloak.realm(realm).users().create(userRep)) {
            if (response.getStatus() == 201) {
                String path = response.getLocation().getPath();
                return path.substring(path.lastIndexOf("/") + 1);
            } else {
                log.error("Failed to create user in Keycloak. Status: {}, Reason: {}",
                        response.getStatus(), response.readEntity(String.class));
                return null;
            }
        } catch (Exception e) {
            log.error("An exception occurred while creating user in Keycloak: {}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    public void assignRealmRoles(String keycloakId, List<String> roleNames) {
        try {
            var userResource = keycloak.realm(realm).users().get(keycloakId);
            List<RoleRepresentation> realmRoles = keycloak.realm(realm).roles().list();

            List<RoleRepresentation> rolesToAdd = realmRoles.stream()
                    .filter(role -> roleNames.contains(role.getName()))
                    .toList();

            if (!rolesToAdd.isEmpty()) {
                userResource.roles().realmLevel().add(rolesToAdd);
                log.info("Successfully assigned roles '{}' to user with ID '{}'", roleNames, keycloakId);
            } else {
                log.warn("No valid roles to assign for user with ID '{}'", keycloakId);
            }
        } catch (Exception e) {
            log.error("Failed to assign roles '{}' to user with ID '{}': {}", roleNames, keycloakId, e.getMessage(), e);
            throw new RuntimeException("Failed to assign roles in Keycloak: " + e.getMessage());
        }
    }

    @Override
    public void deleteUser(String keycloakId) {
        keycloak.realm(realm).users().delete(keycloakId);
    }

    @Override
    public Optional<String> findUserByEmail(String email) {
        List<UserRepresentation> users = keycloak.realm(realm).users().search(email, true);
        if (!users.isEmpty()) {
            return Optional.of(users.get(0).getId());
        }
        return Optional.empty();
    }
}