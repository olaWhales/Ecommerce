package com.semicolon.ecommerceTask.infrastructure.adapter.output.keycloack;

import com.semicolon.ecommerceTask.application.port.output.KeycloakAdminOutPort;
import com.semicolon.ecommerceTask.domain.model.AdminDomainObject;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j; // Add this import

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil.*;

@Slf4j // Add this annotation
@Component
public class KeycloakAdminAdapter implements KeycloakAdminOutPort {

    private final Keycloak keycloak;
    @Value("${keycloak.realm}")
    private String realm;

    public KeycloakAdminAdapter(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    @Override
    public String createUser(AdminDomainObject admin, String password) {
        UserRepresentation user = new UserRepresentation();
        user.setEmail(admin.getEmail());
        user.setUsername(admin.getEmail());
        user.setFirstName(admin.getFirstName());
        user.setLastName(admin.getLastName());
        user.setEnabled(true);
        user.setAttributes(Collections.singletonMap(ROLE, Collections.singletonList("ADMIN")));

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);

        // FIX: Use the plain-text password parameter, not the hashed password from the AdminDomainObject
        credential.setValue(password);

        credential.setTemporary(false);
        user.setCredentials(Collections.singletonList(credential));

        try (Response response = keycloak.realm(realm).users().create(user)) {
            if (response.getStatus() == 201) {
                String path = response.getLocation().getPath();
                return path.substring(path.lastIndexOf("/") + 1);
            } else {
//                log.error("Failed to create user in Keycloak. Status: {}, Reason: {}",
//                        response.getStatus(), response.readEntity(String.class););
                return FAILED_TO_CREATE_USER_IN_KEYCLOAK;
            }
        } catch (Exception e) {
//            log.error("An exception occurred while creating user in Keycloak: {}", e.getMessage(), e);
            return AN_ERROR_OCCURRED_WHILE_CREATING_USER_IN_KEYCLOAK;
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

    // KeycloakAdminAdapter.java
// ...
    @Override
    public void assignRealmRole(String keycloakId, String roleName) {
        try {
            // Find the user using the ID
            var userResource = keycloak.realm(realm).users().get(keycloakId);

            // Find the realm role to be assigned
            var realmRole = keycloak.realm(realm).roles().get(roleName).toRepresentation();

            // Assign the role to the user
            userResource.roles().realmLevel().add(Collections.singletonList(realmRole));

//            log.info("Successfully assigned role '{}' to user with ID '{}'", roleName, keycloakId);
        } catch (Exception e) {
//            log.error("Failed to assign role '{}' to user with ID '{}': {}", roleName, keycloakId, e.getMessage(), e);
            throw new RuntimeException(FAILED_TO_ASSIGN_ROLE_IN_KEYCLOAK + e.getMessage());
        }
    }
}