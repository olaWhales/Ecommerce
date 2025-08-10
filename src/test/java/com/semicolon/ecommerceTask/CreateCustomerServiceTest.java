package com.semicolon.ecommerceTask;

import com.semicolon.ecommerceTask.application.port.output.KeycloakAdminOutPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.CustomerPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.exception.AdminException;
import com.semicolon.ecommerceTask.domain.exception.ValidationException;
import com.semicolon.ecommerceTask.domain.model.CustomerDomainObject;
import com.semicolon.ecommerceTask.domain.service.CreateCustomerService;
import com.semicolon.ecommerceTask.domain.service.UserRegistrationService;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.CustomerRegistrationDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.CustomerResponseDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.CustomerMapper;
import com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateCustomerService Unit Tests")
class CreateCustomerServiceTest {

    @Mock
    private UserRegistrationService userRegistrationService;

    @Mock
    private KeycloakAdminOutPort keycloakAdminOutPort;

    @Mock
    private CustomerPersistenceOutPort customerPersistenceOutPort;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CreateCustomerService createCustomerService;

    private CustomerRegistrationDto validDto;
    private CustomerResponseDto expectedResponseDto;

    @BeforeEach
    void setUp() {
        validDto = CustomerRegistrationDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("StrongPassword123!")
                .build();

        CustomerDomainObject savedCustomer = CustomerDomainObject.builder()
                .keycloakId("keycloak-id-123")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("encodedPassword")
                .roles(Collections.singletonList("BUYER"))
                .build();

        expectedResponseDto = CustomerResponseDto.builder()
                .id(savedCustomer.getId())
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();
    }

    @Test
    @DisplayName("Should successfully register a new customer with a valid DTO")
    void registerCustomer_success() {
        // Given
        when(userRegistrationService.registerUserInKeycloak(any(), anyString()))
                .thenReturn("keycloak-id-123");
        when(passwordEncoder.encode(anyString()))
                .thenReturn("encodedPassword");
        when(customerMapper.toResponseDto(any(CustomerDomainObject.class)))
                .thenReturn(expectedResponseDto);

        // When
        CustomerResponseDto actualResponse = createCustomerService.registerCustomer(validDto);

        // Then
        assertNotNull(actualResponse);
        assertEquals(expectedResponseDto.getEmail(), actualResponse.getEmail());

        // Verify interactions with mocks
        verify(userRegistrationService, times(1)).registerUserInKeycloak(any(), eq("StrongPassword123!"));
        verify(keycloakAdminOutPort, times(1)).assignRealmRoles(eq("keycloak-id-123"), eq(Collections.singletonList("BUYER")));
        verify(passwordEncoder, times(1)).encode(eq("StrongPassword123!"));
        verify(customerPersistenceOutPort, times(1)).saveCustomer(any(CustomerDomainObject.class));
        verify(customerMapper, times(1)).toResponseDto(any(CustomerDomainObject.class));
    }

    @Test
    @DisplayName("Should throw ValidationException for invalid email format")
    void registerCustomer_invalidEmail_throwsException() {
        // Given
        validDto.setEmail("invalid-email");

        // When & Then
        ValidationException exception = assertThrows(ValidationException.class, () ->
                createCustomerService.registerCustomer(validDto));

        assertEquals(MessageUtil.INVALID_EMAIL, exception.getMessage());
        verify(userRegistrationService, never()).registerUserInKeycloak(any(), any());
    }

    @Test
    @DisplayName("Should throw ValidationException for blank first name")
    void registerCustomer_blankFirstName_throwsException() {
        // Given
        validDto.setFirstName("");

        // When & Then
        ValidationException exception = assertThrows(ValidationException.class, () ->
                createCustomerService.registerCustomer(validDto));

        assertEquals(MessageUtil.FIRST_NAME_REQUIRED, exception.getMessage());
    }

    @Test
    @DisplayName("Should throw ValidationException for blank last name")
    void registerCustomer_blankLastName_throwsException() {
        // Given
        validDto.setLastName(" ");

        // When & Then
        ValidationException exception = assertThrows(ValidationException.class, () ->
                createCustomerService.registerCustomer(validDto));

        assertEquals(MessageUtil.LAST_NAME_REQUIRED, exception.getMessage());
    }

    @Test
    @DisplayName("Should throw ValidationException for invalid password format")
    void registerCustomer_invalidPassword_throwsException() {
        // Given
        validDto.setPassword("weak");

        // When & Then
        ValidationException exception = assertThrows(ValidationException.class, () ->
                createCustomerService.registerCustomer(validDto));

        assertEquals(MessageUtil.INVALID_PASSWORD, exception.getMessage());
    }

    @Test
    @DisplayName("Should throw AdminException if user already exists in Keycloak")
    void registerCustomer_userAlreadyExists_throwsException() {
        // Given
        when(userRegistrationService.registerUserInKeycloak(any(), anyString()))
                .thenThrow(new AdminException(MessageUtil.ADMIN_ALREADY_EXISTS_IN_KEYCLOAK));

        // When & Then
        AdminException exception = assertThrows(AdminException.class, () ->
                createCustomerService.registerCustomer(validDto));

        assertEquals(MessageUtil.ADMIN_ALREADY_EXISTS_IN_KEYCLOAK, exception.getMessage());
        verify(customerPersistenceOutPort, never()).saveCustomer(any());
    }

    @Test
    @DisplayName("Should throw AdminException if Keycloak user creation fails")
    void registerCustomer_keycloakCreationFails_throwsException() {
        // Given
        when(userRegistrationService.registerUserInKeycloak(any(), anyString()))
                .thenThrow(new AdminException(MessageUtil.KEYCLOAK_CREATION_FAILED));

        // When & Then
        AdminException exception = assertThrows(AdminException.class, () ->
                createCustomerService.registerCustomer(validDto));

        assertEquals(MessageUtil.KEYCLOAK_CREATION_FAILED, exception.getMessage());
        verify(customerPersistenceOutPort, never()).saveCustomer(any());
    }
}