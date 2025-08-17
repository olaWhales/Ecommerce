package com.semicolon.ecommerceTask;

import com.semicolon.ecommerceTask.application.port.output.EmailOutPort;
import com.semicolon.ecommerceTask.application.port.output.KeycloakAdminOutPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.AdminPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.exception.AdminNotFoundException;
import com.semicolon.ecommerceTask.domain.exception.ValidationException;
import com.semicolon.ecommerceTask.domain.model.AdminDomainObject;
import com.semicolon.ecommerceTask.domain.model.PendingRegistrationDomainObject;
import com.semicolon.ecommerceTask.domain.service.AdminService;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.adminRequestDto.AdminRegistrationRequest;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.AdminResponse;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.enumPackage.UserRole;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.AdminMapper;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.PendingRegistrationMapper;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private AdminPersistenceOutPort adminPersistenceOutPort;
    @Mock
    private KeycloakAdminOutPort keycloakAdminOutPort;
    @Mock
    private EmailOutPort emailOutPort;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AdminMapper adminMapper;
    @Mock
    private PendingRegistrationMapper pendingRegistrationMapper;

    @InjectMocks
    private AdminService adminService;

    private static final String VALID_EMAIL = "test@example.com";
    private static final String VALID_FIRST_NAME = "John";
    private static final String VALID_LAST_NAME = "Doe";
    private static final String VALID_PASSWORD = "Pass123!";
    private static final String INVALID_EMAIL = "invalid-email";
    private static final String WEAK_PASSWORD = "weak";
    private static final String TOKEN = "test-token";
    private static final UUID ADMIN_ID = UUID.randomUUID();
    private static final String KEYCLOAK_ID = UUID.randomUUID().toString(); // FIX: Use a valid UUID string

    @BeforeEach
    void setUp() {
        reset(adminPersistenceOutPort, keycloakAdminOutPort, emailOutPort, passwordEncoder, adminMapper, pendingRegistrationMapper);
    }

    /**
     * Helper method to create a standard AdminRegistrationRequestDto.
     * This avoids duplicating the common fields across multiple tests.
     */
    private AdminRegistrationRequest createRegistrationDto(String email, String password) {
        AdminRegistrationRequest dto = new AdminRegistrationRequest();
        dto.setEmail(email);
        dto.setFirstName(VALID_FIRST_NAME);
        dto.setLastName(VALID_LAST_NAME);
        dto.setPassword(password);
        return dto;
    }

    /**
     * Helper method to create a PendingRegistrationDomainObject using a builder.
     */
    private PendingRegistrationDomainObject createPendingRegistrationObject(String email, String token, LocalDateTime expiration) {
        return PendingRegistrationDomainObject.builder()
                .email(email)
                .token(token)
                .expiration(expiration)
                .build();
    }

    // Test initiateAdminCreation
    @Test
    void initiateAdminCreation_EmailAlreadyExists_ThrowsAdminException() {
        when(adminPersistenceOutPort.existsByEmail(VALID_EMAIL)).thenReturn(true);
        AdminNotFoundException exception = assertThrows(AdminNotFoundException.class, () ->
                adminService.initiateAdminCreation(VALID_EMAIL));
        assertEquals(MessageUtil.ADMIN_ALREADY_EXISTS.formatted(VALID_EMAIL), exception.getMessage());
        verify(adminPersistenceOutPort).existsByEmail(VALID_EMAIL);
        verifyNoMoreInteractions(adminPersistenceOutPort, emailOutPort, adminMapper);
    }

    @Test
    void initiateAdminCreation_InvalidEmail_ThrowsValidationException() {
        ValidationException exception = assertThrows(ValidationException.class, () ->
                adminService.initiateAdminCreation(INVALID_EMAIL));
        assertEquals(MessageUtil.INVALID_EMAIL, exception.getMessage());
        verifyNoInteractions(adminPersistenceOutPort, emailOutPort, adminMapper);
    }

//    @Test
//    void completeAdminRegistration_Success() {
//        AdminRegistrationRequestDto requestDto = createRegistrationDto(VALID_EMAIL, VALID_PASSWORD);
//
//        LocalDateTime expiration = LocalDateTime.now().plusHours(1);
//        PendingRegistrationDomainObject pending = createPendingRegistrationObject(VALID_EMAIL, TOKEN, expiration);
//        UserDomainObject userDomainObject = UserDomainObject.builder()
//                .email(VALID_EMAIL).firstName(VALID_FIRST_NAME).lastName(VALID_LAST_NAME).build();
//        AdminDomainObject adminDomainObject = AdminDomainObject.builder()
//                .email(VALID_EMAIL).firstName(VALID_FIRST_NAME).lastName(VALID_LAST_NAME).build();
//        AdminResponseDto responseDto = AdminResponseDto.builder()
//                .id(UUID.fromString(KEYCLOAK_ID))
//                .email(VALID_EMAIL)
//                .firstName(VALID_FIRST_NAME)
//                .lastName(VALID_LAST_NAME)
//                .roles(Collections.singletonList("ADMIN"))
//                .build();
//
//        when(adminPersistenceOutPort.findPendingRegistrationByEmail(VALID_EMAIL)).thenReturn(Optional.of(pending));
//        when(adminMapper.toUserDomainObject(requestDto)).thenReturn(userDomainObject);
//        when(adminMapper.toAdminDomainObject(requestDto)).thenReturn(adminDomainObject);
//        when(keycloakAdminOutPort.createUser(any(UserDomainObject.class), eq(VALID_PASSWORD))).thenReturn(KEYCLOAK_ID);
//        when(adminPersistenceOutPort.saveAdmin(any(AdminDomainObject.class))).thenReturn(AdminDomainObject.builder().id(KEYCLOAK_ID).build());
//        when(adminMapper.toResponseDto(any(AdminDomainObject.class))).thenReturn(responseDto);
//
//        AdminResponseDto result = createAdminService.completeAdminRegistration(requestDto);
//
//        assertNotNull(result);
//        assertEquals(responseDto.getId(), result.getId());
//        assertEquals(responseDto.getEmail(), result.getEmail());
//        verify(adminPersistenceOutPort).findPendingRegistrationByEmail(VALID_EMAIL);
//        verify(adminMapper).toUserDomainObject(requestDto);
//        verify(adminMapper).toAdminDomainObject(requestDto);
//        verify(keycloakAdminOutPort).createUser(any(UserDomainObject.class), eq(VALID_PASSWORD));
//        verify(adminPersistenceOutPort).saveAdmin(any(AdminDomainObject.class));
//        verify(adminPersistenceOutPort).deletePendingRegistration(VALID_EMAIL);
//        verify(adminMapper).toResponseDto(any(AdminDomainObject.class));
//        verifyNoMoreInteractions(adminPersistenceOutPort, keycloakAdminOutPort, adminMapper);
//    }

    @Test
    void completeAdminRegistration_InvalidEmail_ThrowsValidationException() {
        ValidationException exception = assertThrows(ValidationException.class, () ->
                adminService.completeAdminRegistration(createRegistrationDto(INVALID_EMAIL, VALID_PASSWORD)));

        assertEquals(MessageUtil.INVALID_EMAIL, exception.getMessage());
        verifyNoInteractions(adminPersistenceOutPort, keycloakAdminOutPort, adminMapper);
    }

    @Test
    void completeAdminRegistration_WeakPassword_ThrowsValidationException() {
        ValidationException exception = assertThrows(ValidationException.class, () ->
                adminService.completeAdminRegistration(createRegistrationDto(VALID_EMAIL, WEAK_PASSWORD)));

        assertEquals(MessageUtil.INVALID_PASSWORD, exception.getMessage());
        verifyNoInteractions(adminPersistenceOutPort, keycloakAdminOutPort, adminMapper);
    }

//    @Test
//    void completeAdminRegistration_KeycloakCreationFailed_ThrowsAdminException() {
//        AdminRegistrationRequestDto requestDto = createRegistrationDto(VALID_EMAIL, VALID_PASSWORD);
//
//        LocalDateTime expiration = LocalDateTime.now().plusHours(1);
//        PendingRegistrationDomainObject pending = createPendingRegistrationObject(VALID_EMAIL, TOKEN, expiration);
//        UserDomainObject userDomainObject = UserDomainObject.builder()
//                .email(VALID_EMAIL).firstName(VALID_FIRST_NAME).lastName(VALID_LAST_NAME).build();
//        AdminDomainObject adminDomainObject = AdminDomainObject.builder()
//                .email(VALID_EMAIL).firstName(VALID_FIRST_NAME).lastName(VALID_LAST_NAME).build();
//
//        when(adminPersistenceOutPort.findPendingRegistrationByEmail(VALID_EMAIL)).thenReturn(Optional.of(pending));
//        when(adminMapper.toUserDomainObject(requestDto)).thenReturn(userDomainObject);
//        when(adminMapper.toAdminDomainObject(requestDto)).thenReturn(adminDomainObject);
//        when(keycloakAdminOutPort.createUser(any(UserDomainObject.class), eq(VALID_PASSWORD))).thenReturn(null);
//
//        AdminNotException exception = assertThrows(AdminNotException.class, () ->
//                createAdminService.completeAdminRegistration(requestDto));
//
//        assertEquals(MessageUtil.KEYCLOAK_CREATION_FAILED, exception.getMessage());
//        verify(adminPersistenceOutPort).findPendingRegistrationByEmail(VALID_EMAIL);
//        verify(adminMapper).toUserDomainObject(requestDto);
//        verify(adminMapper).toAdminDomainObject(requestDto);
//        verify(keycloakAdminOutPort).createUser(any(UserDomainObject.class), eq(VALID_PASSWORD));
//        verifyNoMoreInteractions(adminPersistenceOutPort, keycloakAdminOutPort, adminMapper);
//    }

    @Test
    void deleteAdmin_Success() {
        AdminDomainObject admin = AdminDomainObject.builder()
                .id(KEYCLOAK_ID) // Use the valid ID constant
                .email(VALID_EMAIL)
                .build();
        when(adminPersistenceOutPort.findByEmail(VALID_EMAIL)).thenReturn(Optional.of(admin));

        adminService.deleteAdmin(VALID_EMAIL);

        verify(adminPersistenceOutPort).findByEmail(VALID_EMAIL);
        verify(keycloakAdminOutPort).deleteUser(KEYCLOAK_ID);
        verify(adminPersistenceOutPort).deleteAdmin(VALID_EMAIL);
        verifyNoMoreInteractions(adminPersistenceOutPort, keycloakAdminOutPort, adminMapper);
    }

    @Test
    void deleteAdmin_AdminNotFound_ThrowsAdminException() {
        when(adminPersistenceOutPort.findByEmail(VALID_EMAIL)).thenReturn(Optional.empty());

        AdminNotFoundException exception = assertThrows(AdminNotFoundException.class, () ->
                adminService.deleteAdmin(VALID_EMAIL));

        assertEquals(MessageUtil.ADMIN_NOT_FOUND.formatted(VALID_EMAIL), exception.getMessage());
        verify(adminPersistenceOutPort).findByEmail(VALID_EMAIL);
        verifyNoMoreInteractions(adminPersistenceOutPort, keycloakAdminOutPort, adminMapper);
    }

    @Test
    void deleteAdmin_InvalidEmail_ThrowsValidationException() {
        ValidationException exception = assertThrows(ValidationException.class, () ->
                adminService.deleteAdmin(INVALID_EMAIL));

        assertEquals(MessageUtil.INVALID_EMAIL, exception.getMessage());
        verifyNoInteractions(adminPersistenceOutPort, keycloakAdminOutPort, adminMapper);
    }

    @Test
    void updateAdmin_Success() {
        AdminDomainObject admin = AdminDomainObject.builder()
                .id(KEYCLOAK_ID)
                .email(VALID_EMAIL)
                .build();
        AdminResponse responseDto = AdminResponse.builder()
                .id(UUID.fromString(KEYCLOAK_ID))
                .email(VALID_EMAIL)
                .firstName(VALID_FIRST_NAME)
                .lastName(VALID_LAST_NAME)
                .roles(Collections.singletonList("ADMIN"))
                .build();

        when(adminPersistenceOutPort.findByEmail(VALID_EMAIL)).thenReturn(Optional.of(admin));
        when(adminMapper.toResponseDto(any(AdminDomainObject.class))).thenReturn(responseDto);

        AdminResponse result = adminService.updateAdmin(VALID_EMAIL, VALID_FIRST_NAME, VALID_LAST_NAME);

        assertEquals(UUID.fromString(KEYCLOAK_ID), result.getId());
        assertEquals(VALID_EMAIL, result.getEmail());
        assertEquals(VALID_FIRST_NAME, result.getFirstName());
        assertEquals(VALID_LAST_NAME, result.getLastName());
        verify(adminPersistenceOutPort).findByEmail(VALID_EMAIL);
        verify(adminPersistenceOutPort).saveAdmin(any(AdminDomainObject.class));
        verify(adminMapper).toResponseDto(any(AdminDomainObject.class));
        verifyNoMoreInteractions(adminPersistenceOutPort, adminMapper);
    }

    @Test
    void updateAdmin_AdminNotFound_ThrowsAdminException() {
        when(adminPersistenceOutPort.findByEmail(VALID_EMAIL)).thenReturn(Optional.empty());

        AdminNotFoundException exception = assertThrows(AdminNotFoundException.class, () ->
                adminService.updateAdmin(VALID_EMAIL, VALID_FIRST_NAME, VALID_LAST_NAME));

        assertEquals(MessageUtil.ADMIN_NOT_FOUND.formatted(VALID_EMAIL), exception.getMessage());
        verify(adminPersistenceOutPort).findByEmail(VALID_EMAIL);
        verifyNoMoreInteractions(adminPersistenceOutPort, adminMapper);
    }

    @Test
    void updateAdmin_InvalidEmail_ThrowsValidationException() {
        ValidationException exception = assertThrows(ValidationException.class, () ->
                adminService.updateAdmin(INVALID_EMAIL, VALID_FIRST_NAME, VALID_LAST_NAME));

        assertEquals(MessageUtil.INVALID_EMAIL, exception.getMessage());
        verifyNoInteractions(adminPersistenceOutPort, adminMapper);
    }

    @Test
    void getAllAdmins_Success() {
        AdminDomainObject admin = AdminDomainObject.builder()
                .id(KEYCLOAK_ID)
                .email(VALID_EMAIL)
                .firstName(VALID_FIRST_NAME)
                .lastName(VALID_LAST_NAME)
                .roles(Collections.singletonList(UserRole.valueOf("ADMIN")))
                .build();
        AdminResponse responseDto = AdminResponse.builder()
                .id(UUID.fromString(KEYCLOAK_ID))
                .email(VALID_EMAIL)
                .firstName(VALID_FIRST_NAME)
                .lastName(VALID_LAST_NAME)
                .roles(Collections.singletonList("ADMIN"))
                .build();
        List<AdminDomainObject> admins = Collections.singletonList(admin);
        List<AdminResponse> responseDtos = Collections.singletonList(responseDto);

        when(adminPersistenceOutPort.findAllAdmins()).thenReturn(admins);
        when(adminMapper.toResponseDtoList(admins)).thenReturn(responseDtos);

        List<AdminResponse> result = adminService.getAllAdmins();

        assertEquals(1, result.size());
        assertEquals(UUID.fromString(KEYCLOAK_ID), result.get(0).getId());
        assertEquals(VALID_EMAIL, result.get(0).getEmail());
        assertEquals(VALID_FIRST_NAME, result.get(0).getFirstName());
        assertEquals(VALID_LAST_NAME, result.get(0).getLastName());
        verify(adminPersistenceOutPort).findAllAdmins();
        verify(adminMapper).toResponseDtoList(admins);
        verifyNoMoreInteractions(adminPersistenceOutPort, adminMapper);
    }

    @Test
    void getAllAdmins_EmptyList_Success() {
        List<AdminDomainObject> admins = Collections.emptyList();
        List<AdminResponse> responseDtos = Collections.emptyList();

        when(adminPersistenceOutPort.findAllAdmins()).thenReturn(admins);
        when(adminMapper.toResponseDtoList(admins)).thenReturn(responseDtos);

        List<AdminResponse> result = adminService.getAllAdmins();

        assertTrue(result.isEmpty());
        verify(adminPersistenceOutPort).findAllAdmins();
        verify(adminMapper).toResponseDtoList(admins);
        verifyNoMoreInteractions(adminPersistenceOutPort, adminMapper);
    }
}