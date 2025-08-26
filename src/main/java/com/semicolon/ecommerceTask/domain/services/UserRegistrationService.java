package com.semicolon.ecommerceTask.domain.services;

import com.semicolon.ecommerceTask.application.port.output.KeycloakAdminOutPort;
import com.semicolon.ecommerceTask.domain.exception.AdminNotFoundException;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.enumPackages.UserRole;
import com.semicolon.ecommerceTask.infrastructure.utilities.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

import static com.semicolon.ecommerceTask.infrastructure.utilities.MessageUtil.KEYCLOAK_CREATION_FAILED;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserRegistrationService {
    private final KeycloakAdminOutPort keycloakAdminOutPort;

    @Transactional
    public String registerUserInKeycloak(UserDomainObject user, String password) {
        Optional<UserDomainObject> foundUser = keycloakAdminOutPort.findUserByEmail(user.getEmail());
        if (foundUser.isPresent()) {throw new AdminNotFoundException(MessageUtil.ADMIN_ALREADY_EXISTS_IN_KEYCLOAK.formatted(user.getEmail()));}
        String keycloakId = keycloakAdminOutPort.createUser(user, password);
        if (keycloakId == null) {throw new AdminNotFoundException(KEYCLOAK_CREATION_FAILED);}
        keycloakAdminOutPort.assignRealmRoles(keycloakId, Collections.singletonList(UserRole.BUYER));
        return keycloakId;
    }


//    @Transactional
//    public UserDomainObject registerUserInKeycloakAndReturnObject(UserDomainObject user, String password) {
//        Optional<UserDomainObject> foundUser = keycloakAdminOutPort.findUserByEmail(user.getEmail());
//        if (foundUser.isPresent()) {throw new AdminException(MessageUtil.ADMIN_ALREADY_EXISTS_IN_KEYCLOAK.formatted(user.getEmail()));}
//        String keycloakId = keycloakAdminOutPort.createUser(user, password);
//        if (keycloakId == null) {throw new AdminException(KEYCLOAK_CREATION_FAILED);}
//        List<UserRole> roleNames = user.getRoles();
//        keycloakAdminOutPort.assignRealmRoles(keycloakId, roleNames);
//        return user.toBuilder()
//            .id(keycloakId)
//            .build();
//    }
}
