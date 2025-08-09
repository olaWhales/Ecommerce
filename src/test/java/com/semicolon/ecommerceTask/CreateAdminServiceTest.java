package com.semicolon.ecommerceTask;

import com.semicolon.ecommerceTask.application.port.output.EmailOutPort;
import com.semicolon.ecommerceTask.application.port.output.KeycloakAdminOutPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.AdminPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.exception.AdminException;
import com.semicolon.ecommerceTask.domain.exception.ValidationException;
import com.semicolon.ecommerceTask.domain.model.AdminDomainObject;
import com.semicolon.ecommerceTask.domain.model.PendingRegistration;
import com.semicolon.ecommerceTask.domain.service.CreateAdminService;
import com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateAdminServiceTest {

    @Mock
    private AdminPersistenceOutPort adminPersistenceOutPort;

    @Mock
    private KeycloakAdminOutPort keycloakAdminOutPort;

    @Mock
    private EmailOutPort emailOutPort;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateAdminService createAdminService;

    private static final String VALID_EMAIL = "test@example.com";
    private static final String VALID_FIRST_NAME = "John";
    private static final String VALID_LAST_NAME = "Doe";
    private static final String VALID_PASSWORD = "Pass123!";
    private static final String INVALID_EMAIL = "invalid-email";
    private static final String WEAK_PASSWORD = "weak";
    private static final String TOKEN = "test-token";
    private static final UUID ADMIN_ID = UUID.randomUUID();
    private static final String KEYCLOAK_ID = "keycloak-id";

    @BeforeEach
    void setUp() {
        reset(adminPersistenceOutPort, keycloakAdminOutPort, emailOutPort, passwordEncoder);
    }

    // Test initiateAdminCreation
    @Test
    void initiateAdminCreation_Success() {
        when(adminPersistenceOutPort.existsByEmail(VALID_EMAIL)).thenReturn(false);

        createAdminService.initiateAdminCreation(VALID_EMAIL);

        verify(adminPersistenceOutPort).existsByEmail(VALID_EMAIL);
        verify(adminPersistenceOutPort).savePendingRegistration(eq(VALID_EMAIL), anyString(), any(LocalDateTime.class));
        verify(emailOutPort).sendEmail(eq(VALID_EMAIL), eq(MessageUtil.EMAIL_SUBJECT), anyString());
        verifyNoMoreInteractions(adminPersistenceOutPort, emailOutPort);
    }

    @Test
    void initiateAdminCreation_EmailAlreadyExists_ThrowsAdminException() {
        when(adminPersistenceOutPort.existsByEmail(VALID_EMAIL)).thenReturn(true);

        AdminException exception = assertThrows(AdminException.class, () ->
                createAdminService.initiateAdminCreation(VALID_EMAIL));

        assertEquals(MessageUtil.ADMIN_ALREADY_EXISTS.formatted(VALID_EMAIL), exception.getMessage());
        verify(adminPersistenceOutPort).existsByEmail(VALID_EMAIL);
        verifyNoMoreInteractions(adminPersistenceOutPort, emailOutPort);
    }

    @Test
    void initiateAdminCreation_InvalidEmail_ThrowsValidationException() {
        ValidationException exception = assertThrows(ValidationException.class, () ->
                createAdminService.initiateAdminCreation(INVALID_EMAIL));

        assertEquals(MessageUtil.INVALID_EMAIL, exception.getMessage());
        verifyNoInteractions(adminPersistenceOutPort, emailOutPort);
    }

    @Test
    void completeAdminRegistration_Success() {
        LocalDateTime expiration = LocalDateTime.now().plusHours(1);
        PendingRegistration pending = new PendingRegistration(VALID_EMAIL, TOKEN, expiration);
        when(adminPersistenceOutPort.findPendingTokenByEmail(VALID_EMAIL)).thenReturn(Optional.of(pending));
        when(passwordEncoder.encode(VALID_PASSWORD)).thenReturn("encodedPassword");
        when(keycloakAdminOutPort.createUser(any(AdminDomainObject.class))).thenReturn(KEYCLOAK_ID);

        createAdminService.completeAdminRegistration(VALID_EMAIL, VALID_FIRST_NAME, VALID_LAST_NAME, VALID_PASSWORD);

        verify(adminPersistenceOutPort).findPendingTokenByEmail(VALID_EMAIL);
        verify(adminPersistenceOutPort).saveAdmin(any(AdminDomainObject.class));
        verify(adminPersistenceOutPort).deletePendingRegistration(VALID_EMAIL);
        verify(keycloakAdminOutPort).createUser(any(AdminDomainObject.class));
        verifyNoMoreInteractions(adminPersistenceOutPort, keycloakAdminOutPort);
    }

    @Test
    void completeAdminRegistration_TokenExpired_ThrowsAdminException() {
        LocalDateTime expiration = LocalDateTime.now().minusHours(1);
        PendingRegistration pending = new PendingRegistration(VALID_EMAIL, TOKEN, expiration);
        when(adminPersistenceOutPort.findPendingTokenByEmail(VALID_EMAIL)).thenReturn(Optional.of(pending));

        AdminException exception = assertThrows(AdminException.class, () ->
                createAdminService.completeAdminRegistration(VALID_EMAIL, VALID_FIRST_NAME, VALID_LAST_NAME, VALID_PASSWORD));

        assertEquals(MessageUtil.TOKEN_EXPIRED, exception.getMessage());
        verify(adminPersistenceOutPort).findPendingTokenByEmail(VALID_EMAIL);
        verify(adminPersistenceOutPort).deletePendingRegistration(VALID_EMAIL);
        verifyNoMoreInteractions(adminPersistenceOutPort, keycloakAdminOutPort);
    }

    @Test
    void completeAdminRegistration_NoPendingRegistration_ThrowsAdminException() {
        when(adminPersistenceOutPort.findPendingTokenByEmail(VALID_EMAIL)).thenReturn(Optional.empty());

        AdminException exception = assertThrows(AdminException.class, () ->
                createAdminService.completeAdminRegistration(VALID_EMAIL, VALID_FIRST_NAME, VALID_LAST_NAME, VALID_PASSWORD));

        assertEquals(MessageUtil.NO_PENDING_REGISTRATION.formatted(VALID_EMAIL), exception.getMessage());
        verify(adminPersistenceOutPort).findPendingTokenByEmail(VALID_EMAIL);
        verifyNoMoreInteractions(adminPersistenceOutPort, keycloakAdminOutPort);
    }

    @Test
    void completeAdminRegistration_InvalidEmail_ThrowsValidationException() {
        ValidationException exception = assertThrows(ValidationException.class, () ->
                createAdminService.completeAdminRegistration(INVALID_EMAIL, VALID_FIRST_NAME, VALID_LAST_NAME, VALID_PASSWORD));

        assertEquals(MessageUtil.INVALID_EMAIL, exception.getMessage());
        verifyNoInteractions(adminPersistenceOutPort, keycloakAdminOutPort);
    }

    @Test
    void completeAdminRegistration_WeakPassword_ThrowsValidationException() {
        ValidationException exception = assertThrows(ValidationException.class, () ->
                createAdminService.completeAdminRegistration(VALID_EMAIL, VALID_FIRST_NAME, VALID_LAST_NAME, WEAK_PASSWORD));

        assertEquals(MessageUtil.INVALID_PASSWORD, exception.getMessage());
        verifyNoInteractions(adminPersistenceOutPort, keycloakAdminOutPort);
    }

    @Test
    void completeAdminRegistration_KeycloakCreationFailed_ThrowsAdminException() {
        LocalDateTime expiration = LocalDateTime.now().plusHours(1);
        PendingRegistration pending = new PendingRegistration(VALID_EMAIL, TOKEN, expiration);
        when(adminPersistenceOutPort.findPendingTokenByEmail(VALID_EMAIL)).thenReturn(Optional.of(pending));
        when(passwordEncoder.encode(VALID_PASSWORD)).thenReturn("encodedPassword");
        when(keycloakAdminOutPort.createUser(any(AdminDomainObject.class))).thenReturn(null);

        AdminException exception = assertThrows(AdminException.class, () ->
                createAdminService.completeAdminRegistration(VALID_EMAIL, VALID_FIRST_NAME, VALID_LAST_NAME, VALID_PASSWORD));

        assertEquals(MessageUtil.KEYCLOAK_CREATION_FAILED, exception.getMessage());
        verify(adminPersistenceOutPort).findPendingTokenByEmail(VALID_EMAIL);
        verifyNoMoreInteractions(adminPersistenceOutPort, keycloakAdminOutPort);
    }

    // Test deleteAdmin
    @Test
    void deleteAdmin_Success() {
        AdminDomainObject admin = AdminDomainObject.builder()
                .id(ADMIN_ID)
                .email(VALID_EMAIL)
                .keycloakId(KEYCLOAK_ID)
                .build();
        when(adminPersistenceOutPort.findByEmail(VALID_EMAIL)).thenReturn(Optional.of(admin));

        createAdminService.deleteAdmin(VALID_EMAIL);

        verify(adminPersistenceOutPort).findByEmail(VALID_EMAIL);
        verify(keycloakAdminOutPort).deleteUser(KEYCLOAK_ID);
        verify(adminPersistenceOutPort).deleteAdmin(VALID_EMAIL);
        verifyNoMoreInteractions(adminPersistenceOutPort, keycloakAdminOutPort);
    }

    @Test
    void deleteAdmin_AdminNotFound_ThrowsAdminException() {
        when(adminPersistenceOutPort.findByEmail(VALID_EMAIL)).thenReturn(Optional.empty());

        AdminException exception = assertThrows(AdminException.class, () ->
                createAdminService.deleteAdmin(VALID_EMAIL));

        assertEquals(MessageUtil.ADMIN_NOT_FOUND.formatted(VALID_EMAIL), exception.getMessage());
        verify(adminPersistenceOutPort).findByEmail(VALID_EMAIL);
        verifyNoMoreInteractions(adminPersistenceOutPort, keycloakAdminOutPort);
    }

    @Test
    void deleteAdmin_InvalidEmail_ThrowsValidationException() {
        ValidationException exception = assertThrows(ValidationException.class, () ->
                createAdminService.deleteAdmin(INVALID_EMAIL));

        assertEquals(MessageUtil.INVALID_EMAIL, exception.getMessage());
        verifyNoInteractions(adminPersistenceOutPort, keycloakAdminOutPort);
    }

    // Test updateAdmin
    @Test
    void updateAdmin_Success() {
        AdminDomainObject admin = AdminDomainObject.builder()
                .id(ADMIN_ID)
                .email(VALID_EMAIL)
                .build();
        when(adminPersistenceOutPort.findByEmail(VALID_EMAIL)).thenReturn(Optional.of(admin));

        createAdminService.updateAdmin(VALID_EMAIL, VALID_FIRST_NAME, VALID_LAST_NAME);

        verify(adminPersistenceOutPort).findByEmail(VALID_EMAIL);
        verify(adminPersistenceOutPort).saveAdmin(any(AdminDomainObject.class));
        verifyNoMoreInteractions(adminPersistenceOutPort);
    }

    @Test
    void updateAdmin_AdminNotFound_ThrowsAdminException() {
        when(adminPersistenceOutPort.findByEmail(VALID_EMAIL)).thenReturn(Optional.empty());

        AdminException exception = assertThrows(AdminException.class, () ->
                createAdminService.updateAdmin(VALID_EMAIL, VALID_FIRST_NAME, VALID_LAST_NAME));

        assertEquals(MessageUtil.ADMIN_NOT_FOUND.formatted(VALID_EMAIL), exception.getMessage());
        verify(adminPersistenceOutPort).findByEmail(VALID_EMAIL);
        verifyNoMoreInteractions(adminPersistenceOutPort);
    }

    @Test
    void updateAdmin_InvalidEmail_ThrowsValidationException() {
        ValidationException exception = assertThrows(ValidationException.class, () ->
                createAdminService.updateAdmin(INVALID_EMAIL, VALID_FIRST_NAME, VALID_LAST_NAME));

        assertEquals(MessageUtil.INVALID_EMAIL, exception.getMessage());
        verifyNoInteractions(adminPersistenceOutPort);
    }

    // Test getAllAdmins
    @Test
    void getAllAdmins_Success() {
        List<AdminDomainObject> admins = Collections.singletonList(
                AdminDomainObject.builder().id(ADMIN_ID).email(VALID_EMAIL).build());
        when(adminPersistenceOutPort.findAllAdmins()).thenReturn(admins);

        List<AdminDomainObject> result = createAdminService.getAllAdmins();

        assertEquals(1, result.size());
        assertEquals(ADMIN_ID, result.get(0).getId());
        verify(adminPersistenceOutPort).findAllAdmins();
        verifyNoMoreInteractions(adminPersistenceOutPort);
    }

    @Test
    void getAllAdmins_EmptyList_Success() {
        when(adminPersistenceOutPort.findAllAdmins()).thenReturn(Collections.emptyList());

        List<AdminDomainObject> result = createAdminService.getAllAdmins();

        assertTrue(result.isEmpty());
        verify(adminPersistenceOutPort).findAllAdmins();
        verifyNoMoreInteractions(adminPersistenceOutPort);
    }
}