package com.semicolon.ecommerceTask.domain.services;

import com.semicolon.ecommerceTask.application.port.input.CreateSuperAdminUseCase;
import com.semicolon.ecommerceTask.application.port.output.persistence.UserPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.configurations.superAdminProperties.SuperadminProperties;
import com.semicolon.ecommerceTask.infrastructure.output.keycloack.KeycloakUserAdapter;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.mapper.SuperAdminMapper;
import com.semicolon.ecommerceTask.infrastructure.utilities.MessageUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
//@Slf4j
public class SuperAdminService implements CreateSuperAdminUseCase {

    private static final Logger logger = LoggerFactory.getLogger(SuperAdminService.class);

    private final UserPersistenceOutPort userPersistenceOutPort;
    private final KeycloakUserAdapter keycloakUserAdapter;
    private final PasswordEncoder passwordEncoder;
    private final SuperadminProperties properties;
    private final SuperAdminMapper superAdminMapper;


    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void createSuperAdmin() {
        String superAdminEmail = properties.getEmail();
        String superAdminFirstName = properties.getFirstname();
        String superAdminLastName = properties.getLastname();
        String superAdminPassword = properties.getPassword();
        if (superAdminEmail == null || superAdminFirstName == null || superAdminLastName == null || superAdminPassword == null) {
            throw new IllegalArgumentException(MessageUtil.SUPERADMIN_CONFIGURATION_PROPERTY_ARE_MISSING_IN_APPLICATION_PROPERTIES);}
        if (userPersistenceOutPort.existsByEmail(superAdminEmail)) return;
        UserDomainObject superAdmin = superAdminMapper.toDomain(
            superAdminFirstName,
            superAdminLastName,
            superAdminEmail,
            superAdminPassword
        );
        String keycloakId;
        try {
            keycloakId = keycloakUserAdapter.createUser(superAdmin);
            if (keycloakId == null) return;
        } catch (Exception exception) {throw new IllegalArgumentException(MessageUtil.FAILED_TO_CREATE_SUPERADMIN_IN_KEYCLOAK + exception.getMessage(), exception);}
        UserDomainObject updatedSuperAdmin = superAdminMapper.updateWithKeycloak(superAdmin, superAdminPassword, keycloakId);
        userPersistenceOutPort.saveUser(updatedSuperAdmin);
    }
}
