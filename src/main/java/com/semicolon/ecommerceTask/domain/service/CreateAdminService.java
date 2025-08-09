package com.semicolon.ecommerceTask.domain.service;

import com.semicolon.ecommerceTask.application.port.input.CreateAdminUseCase;
import com.semicolon.ecommerceTask.application.port.output.EmailOutPort;
import com.semicolon.ecommerceTask.application.port.output.KeycloakAdminOutPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.AdminPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.exception.AdminException;
import com.semicolon.ecommerceTask.domain.exception.ValidationException;
import com.semicolon.ecommerceTask.domain.model.AdminDomainObject;
import com.semicolon.ecommerceTask.domain.model.PendingRegistration;
import com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class CreateAdminService implements CreateAdminUseCase {

    private final AdminPersistenceOutPort adminPersistenceOutPort;
    private final KeycloakAdminOutPort keycloakAdminOutPort;
    private final EmailOutPort emailOutPort;
    private final PasswordEncoder passwordEncoder;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=\\S+$).{8,}$");

    @Transactional
    public void initiateAdminCreation(String adminEmail) {
        validateEmail(adminEmail);
        if (adminPersistenceOutPort.existsByEmail(adminEmail)) {throw new AdminException(MessageUtil.ADMIN_ALREADY_EXISTS.formatted(adminEmail));}
        String token = UUID.randomUUID().toString();
        LocalDateTime expiration = LocalDateTime.now().plusHours(24);
        adminPersistenceOutPort.savePendingRegistration(adminEmail, token, expiration);
        sendRegistrationEmail(adminEmail, token);
    }

    @Transactional
    public void completeAdminRegistration(String email, String firstName, String lastName, String password) {
        validateRegistrationInput(email, firstName, lastName, password);
        var pending = adminPersistenceOutPort.findPendingTokenByEmail(email).orElseThrow(() -> new AdminException(MessageUtil.NO_PENDING_REGISTRATION.formatted(email)));
        if (LocalDateTime.now().isAfter(pending.expiration())) {adminPersistenceOutPort.deletePendingRegistration(email);throw new AdminException(MessageUtil.TOKEN_EXPIRED);}
        String encodedPassword = passwordEncoder.encode(password);
        AdminDomainObject admin = AdminDomainObject.builder()
                .id(UUID.randomUUID())
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .password(encodedPassword)
                .roles(Collections.singletonList("ADMIN"))
                .build();
        String keycloakId = keycloakAdminOutPort.createUser(admin);
        if (keycloakId == null) {throw new AdminException(MessageUtil.KEYCLOAK_CREATION_FAILED);}

        admin.setKeycloakId(keycloakId);
        adminPersistenceOutPort.saveAdmin(admin);
        adminPersistenceOutPort.deletePendingRegistration(email);
    }

    public void sendRegistrationEmail(String email, String token) {
        String subject = MessageUtil.EMAIL_SUBJECT;
        String body = MessageUtil.EMAIL_BODY.formatted(token, email);
        emailOutPort.sendEmail(email, subject, body);
    }

    @Transactional
    public void deleteAdmin(String email) {
        validateEmail(email);
        AdminDomainObject admin = adminPersistenceOutPort.findByEmail(email).orElseThrow(() -> new AdminException(MessageUtil.ADMIN_NOT_FOUND.formatted(email)));
        keycloakAdminOutPort.deleteUser(admin.getKeycloakId());
        adminPersistenceOutPort.deleteAdmin(email);
    }

    @Transactional
    public void updateAdmin(String email, String firstName, String lastName) {
        validateUpdateInput(email, firstName, lastName);
        AdminDomainObject admin = adminPersistenceOutPort.findByEmail(email).orElseThrow(() -> new AdminException(MessageUtil.ADMIN_NOT_FOUND.formatted(email)));
        admin.setFirstName(firstName);
        admin.setLastName(lastName);
        adminPersistenceOutPort.saveAdmin(admin);
    }

    public List<AdminDomainObject> getAllAdmins() {return adminPersistenceOutPort.findAllAdmins();}

    private void validateEmail(String email) {if (email == null || email.trim().isEmpty() || !EMAIL_PATTERN.matcher(email).matches()) {throw new ValidationException(MessageUtil.INVALID_EMAIL);}}

    private void validateRegistrationInput(String email, String firstName, String lastName, String password) {
        validateEmail(email);
        if (firstName == null || firstName.trim().isEmpty()) {throw new ValidationException(MessageUtil.FIRST_NAME_REQUIRED);}
        if (lastName == null || lastName.trim().isEmpty()) {throw new ValidationException(MessageUtil.LAST_NAME_REQUIRED);}
        if (password == null || !PASSWORD_PATTERN.matcher(password).matches()) {throw new ValidationException(MessageUtil.INVALID_PASSWORD);}
    }

    private void validateUpdateInput(String email, String firstName, String lastName) {
        validateEmail(email);
        if (firstName == null || firstName.trim().isEmpty()) {throw new ValidationException(MessageUtil.FIRST_NAME_REQUIRED);}
        if (lastName == null || lastName.trim().isEmpty()) {throw new ValidationException(MessageUtil.LAST_NAME_REQUIRED);}
    }
}