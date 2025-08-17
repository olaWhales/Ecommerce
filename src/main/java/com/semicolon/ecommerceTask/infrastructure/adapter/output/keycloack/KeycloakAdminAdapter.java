//package com.semicolon.ecommerceTask.infrastructure.adapter.output.keycloack;
//
//import com.semicolon.ecommerceTask.application.port.output.KeycloakAdminOutPort;
//import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
//import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.AdminMapper;
//import jakarta.ws.rs.core.Response;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.keycloak.admin.client.Keycloak;
//import org.keycloak.admin.client.resource.UserResource;
//import org.keycloak.admin.client.resource.UsersResource;
//import org.keycloak.representations.idm.CredentialRepresentation;
//import org.keycloak.representations.idm.RoleRepresentation;
//import org.keycloak.representations.idm.UserRepresentation;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class KeycloakAdminAdapter implements KeycloakAdminOutPort {
//
//    private final Keycloak keycloakAdminClient;
//
//    @Value("${keycloak.admin.realm}")
//    private String realm;
//    private final AdminMapper adminMapper;
//
//    @Override
//    public String createUser(UserDomainObject user, String password) {
//        UserRepresentation userRepresentation = new UserRepresentation();
//        userRepresentation.setUsername(user.getEmail());
//        userRepresentation.setEmail(user.getEmail());
//        userRepresentation.setFirstName(user.getFirstName());
//        userRepresentation.setLastName(user.getLastName());
//        userRepresentation.setEnabled(true);
//        userRepresentation.setEmailVerified(true);
//
//        Response response = keycloakAdminClient.realm(realm).users().create(userRepresentation);
//        String keycloakId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
//
//        CredentialRepresentation passwordCred = new CredentialRepresentation();
//        passwordCred.setType(CredentialRepresentation.PASSWORD);
//        passwordCred.setValue(password);
//        passwordCred.setTemporary(false);
//
//        keycloakAdminClient.realm(realm).users().get(keycloakId).resetPassword(passwordCred);
//        return keycloakId;
//    }
//
//    @Override
//    public void deleteUser(String keycloakId) {
//        keycloakAdminClient.realm(realm).users().delete(keycloakId);
//    }
//
//    @Override
//    public UserDomainObject findUserByEmail(String email) {
//        log.info("KeycloakAdminAdapter: Searching for user with email '{}' in realm '{}'", email, realm);
//        return adminMapper.mapToUserRepresentation(getUserPresentation(email));
//    }
//    private UserRepresentation getUserPresentation(String email){
//        return keycloakAdminClient.realm(realm).
//                users().searchByEmail(email, Boolean.TRUE).stream().findFirst().orElseThrow(()-> new RuntimeException("user not found"));
//    }
//
//    @Override
//    public void assignRealmRoles(String keycloakId, List<String> roleNames) {
//        List<RoleRepresentation> roles = keycloakAdminClient.realm(realm).roles().list();
//        List<RoleRepresentation> realmRoles = roles.stream()
//            .filter(role -> roleNames.contains(role.getName()))
//            .toList();
//        UserResource userResource = keycloakAdminClient.realm(realm).users().get(keycloakId);
//        userResource.roles().realmLevel().add(realmRoles);
//    }
//
//    @Override
//    public void removeRealmRole(String keycloakId, String roleName) {
//        RoleRepresentation roleToRemove = keycloakAdminClient.realm(realm).roles().get(roleName).toRepresentation();
//        UserResource userResource = keycloakAdminClient.realm(realm).users().get(keycloakId);
//        // Corrected API call
//        userResource.roles().realmLevel().remove(Collections.singletonList(roleToRemove));
//    }
//}
package com.semicolon.ecommerceTask.infrastructure.adapter.output.keycloack;

import com.semicolon.ecommerceTask.application.port.output.KeycloakAdminOutPort;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.enumPackage.UserRole;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.AdminMapper;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
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
    private final AdminMapper adminMapper;

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

    @Override
    public Optional<UserDomainObject> findUserByEmail(String email) {
        return getUserPresentation(email).map(adminMapper::mapToUserRepresentation);
    }

    private Optional<UserRepresentation> getUserPresentation(String email) {
        return keycloakAdminClient.realm(realm)
            .users()
            .searchByEmail(email, Boolean.TRUE)
            .stream()
            .findFirst();
    }

    @Override
    public void assignRealmRoles(String keycloakId, List<UserRole> roleNames) {
        List<String> roleNameStrings = roleNames.stream()
            .map(UserRole::name)
            .toList();
        List<RoleRepresentation> roles = keycloakAdminClient.realm(realm).roles().list();
        List<RoleRepresentation> realmRoles = roles.stream()
            .filter(role -> roleNameStrings.contains(role.getName()))
            .toList();
        UserResource userResource = keycloakAdminClient.realm(realm).users().get(keycloakId);
        userResource.roles().realmLevel().add(realmRoles);
    }

//    @Override
//    public void removeRealmRole(String keycloakId, List<UserRole> roleNames) {
//        List<String> roleNameStrings = roleNames.stream()
//            .map(UserRole::name)
//            .toList();
//
//        List<RoleRepresentation> roles = keycloakAdminClient.realm(realm).roles().list();
//
//        List<RoleRepresentation> realmRoles = roles.stream()
//            .filter(role -> roleNameStrings.contains(role.getName()))
//            .toList();
//
//        UserResource userResource = keycloakAdminClient.realm(realm).users().get(keycloakId);
//        userResource.roles().realmLevel().remove(realmRoles);
//    }

}