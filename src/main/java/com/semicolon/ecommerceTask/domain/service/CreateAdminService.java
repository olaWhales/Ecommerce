//package com.semicolon.ecommerceTask.domain.service;
//
//import com.semicolon.ecommerceTask.application.port.input.CreateAdminUseCase;
//import com.semicolon.ecommerceTask.application.port.output.EmailOutPort;
//import com.semicolon.ecommerceTask.application.port.output.KeycloakAdminOutPort;
//import com.semicolon.ecommerceTask.application.port.output.persistence.AdminPersistenceOutPort;
//import com.semicolon.ecommerceTask.domain.exception.AdminException;
//import com.semicolon.ecommerceTask.domain.exception.ValidationException;
//import com.semicolon.ecommerceTask.domain.model.AdminDomainObject;
//import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.AdminResponseDto;
//import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.AdminMapper;
//import com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.List;
//import java.util.UUID;
//import java.util.regex.Pattern;
//
//import static com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil.KEYCLOAK_CREATION_FAILED;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class CreateAdminService implements CreateAdminUseCase {
//
//    private final AdminPersistenceOutPort adminPersistenceOutPort;
//    private final KeycloakAdminOutPort keycloakAdminOutPort;
//    private final EmailOutPort emailOutPort;
//    private final PasswordEncoder passwordEncoder;
//    private final AdminMapper adminMapper;
//
//    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
//    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*?])(?=\\S+$).{8,}$");
//
//    @Transactional
//    public String initiateAdminCreation(String adminEmail) {
//        validateEmail(adminEmail);
//        if (adminPersistenceOutPort.existsByEmail(adminEmail)) {
//            throw new AdminException(MessageUtil.ADMIN_ALREADY_EXISTS.formatted(adminEmail));
//        }
//
//        // ADDED: Pre-check Keycloak here to prevent registration for existing Keycloak users.
//        if (keycloakAdminOutPort.findUserByEmail(adminEmail).isPresent()) {
//            throw new AdminException(MessageUtil.ADMIN_ALREADY_EXISTS_IN_KEYCLOAK.formatted(adminEmail));
//        }
//
//        String token = UUID.randomUUID().toString();
//        LocalDateTime expiration = LocalDateTime.now().plusHours(24);
//        adminPersistenceOutPort.savePendingRegistration(adminEmail, token, expiration);
//        sendRegistrationEmail(adminEmail, token);
//        return "Admin initiation successful. Verification email sent to %s".formatted(adminEmail);
//    }
//
//    @Transactional
//    @Override
//    public AdminResponseDto completeAdminRegistration(String email, String firstName, String lastName, String password) {
//        validateRegistrationInput(email, firstName, lastName, password);
//
//        var pending = adminPersistenceOutPort.findPendingTokenByEmail(email)
//                .orElseThrow(() -> new AdminException(MessageUtil.NO_PENDING_REGISTRATION.formatted(email)));
//
//        if (LocalDateTime.now().isAfter(pending.expiration())) {
//            adminPersistenceOutPort.deletePendingRegistration(email);
//            throw new AdminException(MessageUtil.TOKEN_EXPIRED);
//        }
//
//        if (keycloakAdminOutPort.findUserByEmail(email).isPresent()) {
//            throw new AdminException(MessageUtil.ADMIN_ALREADY_EXISTS_IN_KEYCLOAK.formatted(email));
//        }
//
//        AdminDomainObject admin = AdminDomainObject.builder()
//                .email(email)
//                .firstName(firstName)
//                .lastName(lastName)
//                .roles(Collections.singletonList("ADMIN"))
//                .build();
//
//        String keycloakId = keycloakAdminOutPort.createUser(admin, password);
//        if (keycloakId == null) {
//            throw new AdminException(KEYCLOAK_CREATION_FAILED);
//        }
//
//        keycloakAdminOutPort.assignRealmRole(keycloakId, "ADMIN");
//
//        admin.setKeycloakId(keycloakId);
//        // The password should be encoded before saving to your local database
//        admin.setPassword(passwordEncoder.encode(password));
//        adminPersistenceOutPort.saveAdmin(admin);
//        adminPersistenceOutPort.deletePendingRegistration(email);
//
//        return adminMapper.toResponseDto(admin);
//    }
//
//    @Override
//    public String sendRegistrationEmail(String email, String token) {
//        String subject = MessageUtil.EMAIL_SUBJECT;
//        String body = MessageUtil.EMAIL_BODY.formatted(token, email);
//        emailOutPort.sendEmail(email, subject, body);
//        return "Registration email sent to %s".formatted(email);
//    }
//
//    @Transactional
//    @Override
//    public String deleteAdmin(String email) {
//        validateEmail(email);
//        AdminDomainObject admin = adminPersistenceOutPort.findByEmail(email)
//                .orElseThrow(() -> new AdminException(MessageUtil.ADMIN_NOT_FOUND.formatted(email)));
//        keycloakAdminOutPort.deleteUser(admin.getKeycloakId());
//        adminPersistenceOutPort.deleteAdmin(email);
//        return "Admin with email %s deleted successfully".formatted(email);
//    }
//
//    @Transactional
//    @Override
//    public AdminResponseDto updateAdmin(String email, String firstName, String lastName) {
//        validateUpdateInput(email, firstName, lastName);
//        AdminDomainObject admin = adminPersistenceOutPort.findByEmail(email)
//                .orElseThrow(() -> new AdminException(MessageUtil.ADMIN_NOT_FOUND.formatted(email)));
//        admin.setFirstName(firstName);
//        admin.setLastName(lastName);
//        adminPersistenceOutPort.saveAdmin(admin);
//
//        return adminMapper.toResponseDto(admin);
//    }
//
//    @Override
//    public List<AdminResponseDto> getAllAdmins() {
//        return adminMapper.toResponseDtoList(adminPersistenceOutPort.findAllAdmins());
//    }
//
//    private void validateEmail(String email) {
//        if (email == null || email.trim().isEmpty() || !EMAIL_PATTERN.matcher(email).matches()) {
//            throw new ValidationException(MessageUtil.INVALID_EMAIL);
//        }
//    }
//
//    private void validateRegistrationInput(String email, String firstName, String lastName, String password) {
//        validateEmail(email);
//        if (firstName == null || firstName.trim().isEmpty()) {
//            throw new ValidationException(MessageUtil.FIRST_NAME_REQUIRED);
//        }
//        if (lastName == null || lastName.trim().isEmpty()) {
//            throw new ValidationException(MessageUtil.LAST_NAME_REQUIRED);
//        }
//        if (password == null || !PASSWORD_PATTERN.matcher(password).matches()) {
//            throw new ValidationException(MessageUtil.INVALID_PASSWORD);
//        }
//    }
//
//    private void validateUpdateInput(String email, String firstName, String lastName) {
//        validateEmail(email);
//        if (firstName == null || firstName.trim().isEmpty()) {
//            throw new ValidationException(MessageUtil.FIRST_NAME_REQUIRED);
//        }
//        if (lastName == null || lastName.trim().isEmpty()) {
//            throw new ValidationException(MessageUtil.LAST_NAME_REQUIRED);
//        }
//    }
//}

package com.semicolon.ecommerceTask.domain.service;

import com.semicolon.ecommerceTask.application.port.input.CreateAdminUseCase;
import com.semicolon.ecommerceTask.application.port.output.EmailOutPort;
import com.semicolon.ecommerceTask.application.port.output.KeycloakAdminOutPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.AdminPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.exception.AdminException;
import com.semicolon.ecommerceTask.domain.exception.ValidationException;
import com.semicolon.ecommerceTask.domain.model.AdminDomainObject;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.AdminResponseDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.AdminMapper;
import com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import static com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil.KEYCLOAK_CREATION_FAILED;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateAdminService implements CreateAdminUseCase {

    private final AdminPersistenceOutPort adminPersistenceOutPort;
    private final KeycloakAdminOutPort keycloakAdminOutPort;
    private final EmailOutPort emailOutPort;
    private final PasswordEncoder passwordEncoder;
    private final AdminMapper adminMapper;
    private final UserRegistrationService userRegistrationService;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*?])(?=\\S+$).{8,}$");

    @Transactional
    public String initiateAdminCreation(String adminEmail) {
        validateEmail(adminEmail);
        if (adminPersistenceOutPort.existsByEmail(adminEmail)) {
            throw new AdminException(MessageUtil.ADMIN_ALREADY_EXISTS.formatted(adminEmail));
        }

        if (keycloakAdminOutPort.findUserByEmail(adminEmail).isPresent()) {
            throw new AdminException(MessageUtil.ADMIN_ALREADY_EXISTS_IN_KEYCLOAK.formatted(adminEmail));
        }

        String token = UUID.randomUUID().toString();
        LocalDateTime expiration = LocalDateTime.now().plusHours(24);
        adminPersistenceOutPort.savePendingRegistration(adminEmail, token, expiration);
        sendRegistrationEmail(adminEmail, token);
        return "Admin initiation successful. Verification email sent to %s".formatted(adminEmail);
    }

    @Transactional
    @Override
    public AdminResponseDto completeAdminRegistration(String email, String firstName, String lastName, String password) {
        validateRegistrationInput(email, firstName, lastName, password);

        var pending = adminPersistenceOutPort.findPendingTokenByEmail(email)
                .orElseThrow(() -> new AdminException(MessageUtil.NO_PENDING_REGISTRATION.formatted(email)));

        if (LocalDateTime.now().isAfter(pending.expiration())) {
            adminPersistenceOutPort.deletePendingRegistration(email);
            throw new AdminException(MessageUtil.TOKEN_EXPIRED);
        }

        // Use the generic UserDomainObject for Keycloak creation
        UserDomainObject user = UserDomainObject.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .build();

        String keycloakId = userRegistrationService.registerUserInKeycloak(user, password);

        keycloakAdminOutPort.assignRealmRoles(keycloakId, Collections.singletonList("ADMIN"));

        AdminDomainObject admin = AdminDomainObject.builder()
                .keycloakId(keycloakId)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .roles(Collections.singletonList("ADMIN"))
                .password(passwordEncoder.encode(password))
                .build();

        adminPersistenceOutPort.saveAdmin(admin);
        adminPersistenceOutPort.deletePendingRegistration(email);

        return adminMapper.toResponseDto(admin);
    }

    @Override
    public String sendRegistrationEmail(String email, String token) {
        String subject = MessageUtil.EMAIL_SUBJECT;
        String body = MessageUtil.EMAIL_BODY.formatted(token, email);
        emailOutPort.sendEmail(email, subject, body);
        return "Registration email sent to %s".formatted(email);
    }

    @Transactional
    @Override
    public String deleteAdmin(String email) {
        validateEmail(email);
        AdminDomainObject admin = adminPersistenceOutPort.findByEmail(email)
                .orElseThrow(() -> new AdminException(MessageUtil.ADMIN_NOT_FOUND.formatted(email)));
        keycloakAdminOutPort.deleteUser(admin.getKeycloakId());
        adminPersistenceOutPort.deleteAdmin(email);
        return "Admin with email %s deleted successfully".formatted(email);
    }

    @Transactional
    @Override
    public AdminResponseDto updateAdmin(String email, String firstName, String lastName) {
        validateUpdateInput(email, firstName, lastName);
        AdminDomainObject admin = adminPersistenceOutPort.findByEmail(email)
                .orElseThrow(() -> new AdminException(MessageUtil.ADMIN_NOT_FOUND.formatted(email)));
        admin.setFirstName(firstName);
        admin.setLastName(lastName);
        adminPersistenceOutPort.saveAdmin(admin);

        return adminMapper.toResponseDto(admin);
    }

    @Override
    public List<AdminResponseDto> getAllAdmins() {
        return adminMapper.toResponseDtoList(adminPersistenceOutPort.findAllAdmins());
    }

    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty() || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new ValidationException(MessageUtil.INVALID_EMAIL);
        }
    }

    private void validateRegistrationInput(String email, String firstName, String lastName, String password) {
        validateEmail(email);
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new ValidationException(MessageUtil.FIRST_NAME_REQUIRED);
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new ValidationException(MessageUtil.LAST_NAME_REQUIRED);
        }
        if (password == null || !PASSWORD_PATTERN.matcher(password).matches()) {
            throw new ValidationException(MessageUtil.INVALID_PASSWORD);
        }
    }

    private void validateUpdateInput(String email, String firstName, String lastName) {
        validateEmail(email);
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new ValidationException(MessageUtil.FIRST_NAME_REQUIRED);
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new ValidationException(MessageUtil.LAST_NAME_REQUIRED);
        }
    }
}