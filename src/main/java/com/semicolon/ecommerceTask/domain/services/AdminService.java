package com.semicolon.ecommerceTask.domain.services;

import com.semicolon.ecommerceTask.application.port.input.AdminUseCase;
import com.semicolon.ecommerceTask.application.port.output.EmailOutPort;
import com.semicolon.ecommerceTask.application.port.output.KeycloakAdminOutPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.AdminPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.exception.AdminNotFoundException;
import com.semicolon.ecommerceTask.domain.model.AdminDomainObject;
import com.semicolon.ecommerceTask.domain.model.PendingRegistrationDomainObject;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.responses.AdminResponse;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entities.UserRole;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.AdminMapper;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.PendingRegistrationMapper;
import com.semicolon.ecommerceTask.infrastructure.adapter.utilities.AdminRegistrationValidator;
import com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil;
import com.semicolon.ecommerceTask.infrastructure.adapter.utilities.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService implements AdminUseCase {

    private final AdminPersistenceOutPort adminPersistenceOutPort;
    private final KeycloakAdminOutPort keycloakAdminOutPort;
    private final EmailOutPort emailOutPort;
    private final PasswordEncoder passwordEncoder;
    private final AdminMapper adminMapper;
    private final PendingRegistrationMapper pendingRegistrationMapper;
    private final AdminRegistrationValidator adminRegValidator;

    @Transactional
    @Override
    public String initiateAdminCreation(String adminEmail) {
        ValidationUtil.validateEmail(adminEmail);
        adminRegValidator.ensureAdminDoesNotExist(adminEmail);
        String token = adminRegValidator.newRegistrationToken();
        LocalDateTime expiration = adminRegValidator.computeExpiration();
        saveOrUpdatePendingRegistration(adminEmail, token, expiration);
        sendRegistrationEmail(adminEmail, token);
        return MessageUtil.ADMIN_INITIATION_SUCCESSFUL_VERIFICATION_EMAIL_SENT_TO_.formatted(adminEmail);
    }

    @Transactional
    @Override
    public AdminDomainObject completeAdminRegistration(AdminDomainObject admin, UserDomainObject user, String rawPassword) {
        adminRegValidator.getValidPendingRegistrationOrThrow(admin.getEmail());
        String keycloakId = createUserInKeycloak(user, rawPassword);
        adminRegValidator.assignAdminRole(keycloakId);
        AdminDomainObject finalAdmin = AdminDomainObject.builder()
            .id(keycloakId)
            .email(admin.getEmail())
            .firstName(admin.getFirstName())
            .lastName(admin.getLastName())
            .password(passwordEncoder.encode(rawPassword))
            .roles(Collections.singletonList(UserRole.ADMIN))
            .build();
        adminPersistenceOutPort.saveAdmin(finalAdmin);
        adminPersistenceOutPort.deletePendingRegistration(admin.getEmail());
        return finalAdmin;
    }

    @Override
    public String sendRegistrationEmail(String email, String token) {
        String subject = MessageUtil.EMAIL_SUBJECT;
        String body = MessageUtil.EMAIL_BODY.formatted(token, email);
        emailOutPort.sendEmail(email, subject, body);
        return MessageUtil.REGISTRATION_EMAIL_SENT_TO.formatted(email);
    }

    @Transactional
    @Override
    public String deleteAdmin(String email) {
        ValidationUtil.validateEmail(email);
        Optional<AdminDomainObject> adminOptional = adminPersistenceOutPort.findByEmail(email);
        if (adminOptional.isEmpty()) {throw new AdminNotFoundException(MessageUtil.ADMIN_NOT_FOUND.formatted(email));}
        AdminDomainObject admin = adminOptional.get();
        keycloakAdminOutPort.deleteUser(admin.getId());
        adminPersistenceOutPort.deleteAdmin(email);
        return MessageUtil.ADMIN_WITH_EMAIL_DELETED_SUCCESSFUL.formatted(email);
    }

    @Transactional
    @Override
    public AdminResponse updateAdmin(String email, String firstName, String lastName) {
        ValidationUtil.validateUpdateInput(email, firstName, lastName);
        Optional<AdminDomainObject> adminOptional = adminPersistenceOutPort.findByEmail(email);
        if (adminOptional.isEmpty()) {throw new AdminNotFoundException(MessageUtil.ADMIN_NOT_FOUND.formatted(email));}
        AdminDomainObject admin = adminOptional.get();
//        admin.setFirstName(firstName);
//        admin.setLastName(lastName);
        adminPersistenceOutPort.saveAdmin(admin);
        return adminMapper.toResponseDto(admin);
    }

    @Override
    public List<AdminResponse> getAllAdmins() {
        List<AdminDomainObject> admins = adminPersistenceOutPort.findAllAdmins();
        return adminMapper.toResponseDtoList(admins);
    }

    private void saveOrUpdatePendingRegistration(String email, String token, LocalDateTime expiration) {
        Optional<PendingRegistrationDomainObject> existing = adminPersistenceOutPort.findPendingRegistrationByEmail(email);
        PendingRegistrationDomainObject.builder()
            .email(email)
            .token(token)
            .expiration(expiration)
            .build();
        if (existing.isPresent()) {adminPersistenceOutPort.updatePendingRegistration(email, token, expiration);
        } else {adminPersistenceOutPort.createPendingRegistration(email, token, expiration);}
    }

    private String createUserInKeycloak(UserDomainObject user, String rawPassword) {
        String keycloakId = keycloakAdminOutPort.createUser(user, rawPassword);
        if (keycloakId == null) {throw new AdminNotFoundException(MessageUtil.KEYCLOAK_CREATION_FAILED);}
        return keycloakId;
    }
}
