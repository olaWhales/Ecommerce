package com.semicolon.ecommerceTask.domain.service;

import com.semicolon.ecommerceTask.application.port.input.CreateSuperAdminUseCase;
import com.semicolon.ecommerceTask.application.port.output.persistence.UserPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.configuration.superAdminProperties.SuperadminProperties;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.keycloack.KeycloakUserAdapter;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CreateSuperAdminService implements CreateSuperAdminUseCase {

    private static final Logger logger = LoggerFactory.getLogger(CreateSuperAdminService.class);

    private final UserPersistenceOutPort userPersistenceOutPort;
    private final KeycloakUserAdapter keycloakUserAdapter;
    private final PasswordEncoder passwordEncoder;
    private final SuperadminProperties properties;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void createSuperAdmin() {
        logger.info("Loading superadmin properties: email={}, firstname={}, lastname={}, password={}",
                properties.getEmail(), properties.getFirstname(), properties.getLastname(), properties.getPassword());

        String superAdminEmail = properties.getEmail();
        String superAdminFirstName = properties.getFirstname();
        String superAdminLastName = properties.getLastname();
        String superAdminPassword = properties.getPassword();

        if (superAdminEmail == null || superAdminFirstName == null || superAdminLastName == null || superAdminPassword == null) {
            logger.error("Superadmin properties are missing: email={}, firstname={}, lastname={}, password={}",
                    superAdminEmail, superAdminFirstName, superAdminLastName, superAdminPassword);
            throw new IllegalStateException("Superadmin configuration properties are missing in application.properties");
        }

        if (userPersistenceOutPort.existsByEmail(superAdminEmail)) {
            logger.info("User or email {} already exists in local DB", superAdminEmail);
            return;
        }

        UserDomainObject superAdmin = UserDomainObject.builder()
                .firstName(superAdminFirstName)
                .lastName(superAdminLastName)
                .email(superAdminEmail)
                .password(superAdminPassword)
                .roles(Collections.singletonList(UserRole.SUPERADMIN))
                .build();

        String keycloakId;
        try {
            keycloakId = keycloakUserAdapter.createUser(superAdmin);
            if (keycloakId == null) {
                logger.info("User or email {} already exists in Keycloak", superAdminEmail);
                return; // Exit without error if user already exists
            }
            logger.info("Superadmin created or found in Keycloak with ID: {}", keycloakId);
        } catch (Exception e) {
            logger.error("Failed to create superadmin in Keycloak", e);
            throw new RuntimeException("Failed to create superadmin in Keycloak: " + e.getMessage(), e);
        }

        superAdmin.setPassword(passwordEncoder.encode(superAdminPassword));
        superAdmin.setKeycloakId(keycloakId); // Set the Keycloak ID
        userPersistenceOutPort.saveLocalUser(keycloakId, superAdmin); // Pass the updated object
        logger.info("Superadmin saved in local DB with email: {}, firstname: {}, lastname: {}",
                superAdminEmail, superAdminFirstName, superAdminLastName);
    }
}

//package com.semicolon.ecommerceTask.domain.service;
//
//import com.semicolon.ecommerceTask.application.port.input.CreateSuperAdminUseCase;
//import com.semicolon.ecommerceTask.application.port.output.persistence.UserPersistenceOutPort;
//import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
//import com.semicolon.ecommerceTask.infrastructure.adapter.configuration.superAdminProperties.SuperadminProperties;
//import com.semicolon.ecommerceTask.infrastructure.adapter.output.keycloack.KeycloakUserAdapter;
//import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserRole;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Collections;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class CreateSuperAdminService implements CreateSuperAdminUseCase {
//
//    private static final Logger logger = LoggerFactory.getLogger(CreateSuperAdminService.class);
//
//    private final UserPersistenceOutPort userPersistenceOutPort;
//    private final KeycloakUserAdapter keycloakUserAdapter;
//    private final PasswordEncoder passwordEncoder;
//    private final SuperadminProperties properties;
//
//    @EventListener(ApplicationReadyEvent.class)
//    @Transactional
//    public void createSuperAdmins() {
//        if (properties.getDetails() == null || properties.getDetails().isEmpty()) {
//            logger.warn("No superadmin details configured in application.properties");
//            return;
//        }
//
//        for (int i = 0; i < properties.getDetails().size(); i++) {
//            SuperadminProperties.SuperadminDetail detail = properties.getDetails().get(i);
//            String userType = "superadmin-" + i;
//            createUser(userType, detail.getEmail(), detail.getFirstname(), detail.getLastname(),
//                    detail.getPassword(), Collections.singletonList(UserRole.SUPERADMIN));
//        }
//    }
//
//    private void createUser(String userType, String email, String firstName, String lastName,
//                            String password, List<UserRole> roles) {
//        logger.info("Loading {} properties: email={}, firstname={}, lastname={}, password={}",
//                userType, email, firstName, lastName, password);
//
//        if (email == null || firstName == null || lastName == null || password == null) {
//            logger.error("{} properties are missing: email={}, firstname={}, lastname={}, password={}",
//                    userType, email, firstName, lastName, password);
//            throw new IllegalStateException(userType + " configuration properties are missing");
//        }
//
//        if (userPersistenceOutPort.existsByEmail(email)) {
//            logger.info("User or email {} already exists in local DB", email);
//            return;
//        }
//
//        UserDomainObject user = UserDomainObject.builder()
//                .firstName(firstName)
//                .lastName(lastName)
//                .email(email)
//                .password(password)
//                .roles(roles)
//                .build();
//
//        String keycloakId;
//        try {
//            logger.info("Attempting to create {} with password: {}", userType, password);
//            keycloakId = keycloakUserAdapter.createUser(user);
//            if (keycloakId == null) {
//                logger.info("User or email {} already exists in Keycloak", email);
//                return;
//            }
//            logger.info("{} created or found in Keycloak with ID: {}", userType, keycloakId);
//            user.setKeycloakId(keycloakId);
//        } catch (Exception e) {
//            logger.error("Failed to create {} in Keycloak", userType, e);
//            throw new RuntimeException("Failed to create " + userType + " in Keycloak: " + e.getMessage(), e);
//        }
//
//        user.setPassword(passwordEncoder.encode(password));
//        logger.info("Saving to DB: email={}, firstname={}, lastname={}, keycloakId={}",
//                user.getEmail(), user.getFirstName(), user.getLastName(), user.getKeycloakId());
//        userPersistenceOutPort.saveLocalUser(keycloakId, user);
//        logger.info("{} saved in local DB with email: {}, firstname: {}, lastname: {}",
//                userType, email, firstName, lastName);
//    }
//
//}