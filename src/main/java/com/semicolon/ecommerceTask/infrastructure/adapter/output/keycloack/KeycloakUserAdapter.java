package com.semicolon.ecommerceTask.infrastructure.adapter.output.keycloack;

import com.semicolon.ecommerceTask.application.port.output.KeycloakUserPort;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import jakarta.ws.rs.core.Response;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakUserAdapter implements KeycloakUserPort {
    private final Keycloak keycloak;
    private final String realm = "your-realm-name";

//    @Override
//    public void createUserWithRole(KeycloakUserDto userDto) {
//    }
    @Override
    public String createUser(UserDomainObject userDomainObject) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(userDomainObject.getName());
        userRepresentation.setEmail(userDomainObject.getEmail());
        userRepresentation.setEnabled(true);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setValue(userDomainObject.getPassword());

        userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));

        UsersResource usersResource = keycloak.realm(realm).users();
        Response response = usersResource.create(userRepresentation);

        if(response.getStatus() != 201) {
            throw new RuntimeException("Keycloak user creation failed");
        }

        String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
        assignRoles(userId, userDomainObject.getRoles());
        return userId;
    }

    private void assignRoles(String userId, List<UserRole> userRoleList) {
        RealmResource realmResource = keycloak.realm(realm);
        UserResource userResource = realmResource.users().get(userId);

        List<RoleRepresentation> roleRepresentations = userRoleList.stream()
            .map(userRole -> realmResource.roles().get(userRole.name()).toRepresentation())
            .toList();

        userResource.roles().realmLevel().add(roleRepresentations);
    }


    @Override
    public void deleteUser(String userDomainObjectId) {
        keycloak.realm(realm).users().get(userDomainObjectId).remove();
    }

    @Override
    public void update(String userId, UserDomainObject userDomainObject) {
        UserResource usersResource = keycloak.realm(realm).users().get(userId);
        UserRepresentation representation = usersResource.toRepresentation();

        representation.setEmail(userDomainObject.getEmail());
        usersResource.update(representation);

        usersResource.roles().realmLevel().remove(usersResource.roles().realmLevel().listAll());
        assignRoles(userId, userDomainObject.getRoles());
    }
}
