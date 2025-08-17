package com.semicolon.ecommerceTask.domain.service;

import com.semicolon.ecommerceTask.application.port.input.AdminUseCase;
import com.semicolon.ecommerceTask.application.port.output.EmailOutPort;
import com.semicolon.ecommerceTask.application.port.output.KeycloakAdminOutPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.AdminPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.exception.AdminNotFoundException;
import com.semicolon.ecommerceTask.domain.exception.NameNotFoundException;
import com.semicolon.ecommerceTask.domain.model.AdminDomainObject;
import com.semicolon.ecommerceTask.domain.model.PendingRegistrationDomainObject;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.adminRequestDto.AdminRegistrationRequest;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.AdminResponse;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.enumPackage.UserRole;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.AdminMapper;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.PendingRegistrationMapper;
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
import java.util.UUID;

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
    private final ValidationUtil validationUtil;

    private static final long REGISTRATION_TOKEN_VALIDITY_HOURS = 24;

@Transactional
@Override
public String initiateAdminCreation(String adminEmail) {
    ValidationUtil.validateEmail(adminEmail);
    checkIfAdminExists(adminEmail);
    String token = generateRegistrationToken();
    LocalDateTime expiration = LocalDateTime.now().plusHours(REGISTRATION_TOKEN_VALIDITY_HOURS);
    saveOrUpdatePendingRegistration(adminEmail, token, expiration);
    sendRegistrationEmail(adminEmail, token);
    return MessageUtil.ADMIN_INITIATION_SUCCESSFUL_VERIFICATION_EMAIL_SENT_TO_.formatted(adminEmail);
}

@Transactional
@Override
public AdminResponse completeAdminRegistration(AdminRegistrationRequest requestDto) {
    validateRegistrationInput(requestDto.getEmail(), requestDto.getFirstName(), requestDto.getLastName(), requestDto.getPassword());
    PendingRegistrationDomainObject pendingRegistration = validateAndGetPendingRegistration(requestDto.getEmail());
    String keycloakId = createUserInKeycloak(adminMapper.toUserDomainObject(requestDto), requestDto.getPassword());
    AdminDomainObject admin = buildAdminDomainObject(keycloakId, requestDto.getEmail(), requestDto.getFirstName(), requestDto.getLastName(), requestDto.getPassword());
    assignAdminRole(keycloakId);
    adminPersistenceOutPort.saveAdmin(admin);
    adminPersistenceOutPort.deletePendingRegistration(requestDto.getEmail());
    return adminMapper.toResponseDto(admin);
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
        validateUpdateInput(email, firstName, lastName);
        Optional<AdminDomainObject> adminOptional = adminPersistenceOutPort.findByEmail(email);
        if (adminOptional.isEmpty()) {throw new AdminNotFoundException(MessageUtil.ADMIN_NOT_FOUND.formatted(email));}
        AdminDomainObject admin = adminOptional.get();
        adminPersistenceOutPort.saveAdmin(admin);
        return adminMapper.toResponseDto(admin);
    }

    @Override
    public List<AdminResponse> getAllAdmins() {
        List<AdminDomainObject> admins = adminPersistenceOutPort.findAllAdmins();
        return adminMapper.toResponseDtoList(admins);
    }

    private void validateRegistrationInput(String email, String firstName, String lastName, String password) {
        ValidationUtil.validateEmail(email);
        if (firstName == null || firstName.trim().isEmpty()) {throw new NameNotFoundException(MessageUtil.FIRST_NAME_REQUIRED);}
        if (lastName == null || lastName.trim().isEmpty()) {throw new NameNotFoundException(MessageUtil.LAST_NAME_REQUIRED);}
        ValidationUtil.validatePassword(password);
    }

    private void validateUpdateInput(String email, String firstName, String lastName) {
        ValidationUtil.validateEmail(email);
        if (firstName == null || firstName.trim().isEmpty()) {throw new NameNotFoundException(MessageUtil.FIRST_NAME_REQUIRED);}
        if (lastName == null || lastName.trim().isEmpty()) {throw new NameNotFoundException(MessageUtil.LAST_NAME_REQUIRED);}
    }

    private void checkIfAdminExists(String email) {
        if (adminPersistenceOutPort.existsByEmail(email)) {throw new AdminNotFoundException(MessageUtil.ADMIN_ALREADY_EXISTS.formatted(email));}
        Optional<UserDomainObject> existingUser = keycloakAdminOutPort.findUserByEmail(email);
        if (existingUser.isPresent()) {throw new AdminNotFoundException(MessageUtil.ADMIN_ALREADY_EXISTS_IN_KEYCLOAK.formatted(email));}
    }

    private String generateRegistrationToken() {
        return UUID.randomUUID().toString();
    }

    private void saveOrUpdatePendingRegistration(String email, String token, LocalDateTime expiration) {
        Optional<PendingRegistrationDomainObject> existingPendingRegistration = adminPersistenceOutPort.findPendingRegistrationByEmail(email);
        PendingRegistrationDomainObject pendingRegistration = PendingRegistrationDomainObject.builder()
            .email(email)
            .token(token)
            .expiration(expiration)
            .build();
        if (existingPendingRegistration.isPresent()) {adminPersistenceOutPort.updatePendingRegistration(email, token, expiration);
        } else {
            adminPersistenceOutPort.createPendingRegistration(email, token, expiration);
        }
    }

    private PendingRegistrationDomainObject validateAndGetPendingRegistration(String email) {
        Optional<PendingRegistrationDomainObject> pendingOptional = adminPersistenceOutPort.findPendingRegistrationByEmail(email);
        if (pendingOptional.isEmpty()) {throw new AdminNotFoundException(MessageUtil.NO_PENDING_REGISTRATION.formatted(email));}
        PendingRegistrationDomainObject pendingRegistration = pendingRegistrationMapper.toDomain(pendingOptional.get());
        if (LocalDateTime.now().isAfter(pendingRegistration.getExpiration())) {
            adminPersistenceOutPort.deletePendingRegistration(email);
            throw new AdminNotFoundException(MessageUtil.TOKEN_EXPIRED);
        }
        return pendingRegistration;
    }

    private UserDomainObject buildUserDomainObject(String email, String firstName, String lastName) {
        return UserDomainObject.builder()
            .email(email)
            .firstName(firstName)
            .lastName(lastName)
            .build();
    }

    private String createUserInKeycloak(UserDomainObject user, String password) {
        String keycloakId = keycloakAdminOutPort.createUser(user, password);
        if (keycloakId == null) {throw new AdminNotFoundException(MessageUtil.KEYCLOAK_CREATION_FAILED);}
        return keycloakId;
    }
    private void assignAdminRole(String keycloakId) {keycloakAdminOutPort.assignRealmRoles(keycloakId, Collections.singletonList(UserRole.ADMIN));}

    private AdminDomainObject buildAdminDomainObject(String keycloakId, String email, String firstName, String lastName, String password) {
        return AdminDomainObject.builder()
            .id(keycloakId)
            .email(email)
            .firstName(firstName)
            .lastName(lastName)
            .roles(Collections.singletonList(UserRole.ADMIN))
            .password(passwordEncoder.encode(password))
            .build();
    }
}