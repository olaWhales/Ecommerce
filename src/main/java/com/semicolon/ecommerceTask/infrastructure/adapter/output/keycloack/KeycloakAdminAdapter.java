package com.semicolon.ecommerceTask.infrastructure.adapter.output.keycloack;

import com.semicolon.ecommerceTask.application.port.output.KeycloakAdminOutPort;
import com.semicolon.ecommerceTask.domain.model.AdminDomainObject;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class KeycloakAdminAdapter implements KeycloakAdminOutPort {

    private final Keycloak keycloak;
    @Value("${keycloak.realm}")
    private String realm;

    public KeycloakAdminAdapter(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    @Override
    public String createUser(AdminDomainObject admin) {
        UserRepresentation user = new UserRepresentation();
        user.setEmail(admin.getEmail());
        user.setFirstName(admin.getFirstName());
        user.setLastName(admin.getLastName());
        user.setEnabled(true);
        user.setAttributes(Collections.singletonMap("roles", Collections.singletonList("ADMIN")));

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(admin.getPassword());
        credential.setTemporary(false);
        user.setCredentials(Collections.singletonList(credential));

        Response response = keycloak.realm(realm).users().create(user);
        if (response.getStatus() == 201) {
            String path = response.getLocation().getPath();
            return path.substring(path.lastIndexOf("/") + 1);
        }
        return null;
    }

    @Override
    public void deleteUser(String keycloakId) {
        keycloak.realm(realm).users().delete(keycloakId);
    }
}