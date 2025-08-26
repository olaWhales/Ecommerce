// src/test/java/com/semicolon/ecommerceTask/CustomerServiceTest.java
package com.semicolon.ecommerceTask;

import com.semicolon.ecommerceTask.application.port.output.persistence.UserPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.exception.AdminNotFoundException;
import com.semicolon.ecommerceTask.domain.exception.NameNotFoundException;
import com.semicolon.ecommerceTask.domain.model.CustomerDomainObject;
import com.semicolon.ecommerceTask.domain.services.CustomerService;
import com.semicolon.ecommerceTask.domain.services.UserRegistrationService;
import com.semicolon.ecommerceTask.infrastructure.input.data.requests.DefaultRegistrationRequest;
import com.semicolon.ecommerceTask.infrastructure.input.data.responses.UserDomainObjectResponse;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.enumPackages.UserRole;
import com.semicolon.ecommerceTask.infrastructure.utilities.CustomerInputValidator;
import com.semicolon.ecommerceTask.infrastructure.utilities.MessageUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CustomerService Unit Tests")
class CustomerServiceTest {

    @Mock
    private CustomerInputValidator customerInputValidator;

    @Mock
    private UserRegistrationService userRegistrationService;

    @Mock
    private UserPersistenceOutPort userPersistenceOutPort;

    @InjectMocks
    private CustomerService customerService;

    private DefaultRegistrationRequest validDto;
    private UserDomainObjectResponse expectedResponseDto;

    @BeforeEach
    void setUp() {
        validDto = DefaultRegistrationRequest.builder()
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
                .roles(Collections.singletonList(UserRole.valueOf("BUYER")))
                .build();

        expectedResponseDto = UserDomainObjectResponse.builder()
                .id(savedCustomer.getId())
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();
    }

    @Test
    @DisplayName("Should throw ValidationException for invalid email format")
    void defaultUserRegistration_invalidEmail_throwsException() {
        validDto.setEmail("invalid-email");
        doThrow(new NameNotFoundException(MessageUtil.INVALID_EMAIL))
                .when(customerInputValidator).validate(any(DefaultRegistrationRequest.class));
        NameNotFoundException exception = assertThrows(NameNotFoundException.class, () ->
                customerService.defaultUserRegistration(validDto));
        assertEquals(MessageUtil.INVALID_EMAIL, exception.getMessage());
        verify(userRegistrationService, never()).registerUserInKeycloak(any(), any());
        verify(userPersistenceOutPort, never()).saveUser(any());
    }

    @Test
    @DisplayName("Should throw ValidationException for blank first name")
    void defaultUserRegistration_blankFirstName_throwsException() {
        validDto.setFirstName("");
        doThrow(new NameNotFoundException(MessageUtil.FIRST_NAME_REQUIRED))
                .when(customerInputValidator).validate(any(DefaultRegistrationRequest.class));
        NameNotFoundException exception = assertThrows(NameNotFoundException.class, () ->
                customerService.defaultUserRegistration(validDto));
        assertEquals(MessageUtil.FIRST_NAME_REQUIRED, exception.getMessage());
        verify(userPersistenceOutPort, never()).saveUser(any());
    }

    @Test
    @DisplayName("Should throw ValidationException for blank last name")
    void defaultUserRegistration_blankLastName_throwsException() {
        validDto.setLastName(" ");
        doThrow(new NameNotFoundException(MessageUtil.LAST_NAME_REQUIRED))
                .when(customerInputValidator).validate(any(DefaultRegistrationRequest.class));
        NameNotFoundException exception = assertThrows(NameNotFoundException.class, () ->
                customerService.defaultUserRegistration(validDto));
        assertEquals(MessageUtil.LAST_NAME_REQUIRED, exception.getMessage());
        verify(userPersistenceOutPort, never()).saveUser(any());
    }

    @Test
    @DisplayName("Should throw ValidationException for invalid password format")
    void defaultUserRegistration_invalidPassword_throwsException() {
        validDto.setPassword("weak");
        doThrow(new NameNotFoundException(MessageUtil.INVALID_PASSWORD))
                .when(customerInputValidator).validate(any(DefaultRegistrationRequest.class));
        NameNotFoundException exception = assertThrows(NameNotFoundException.class, () ->
                customerService.defaultUserRegistration(validDto));
        assertEquals(MessageUtil.INVALID_PASSWORD, exception.getMessage());
        verify(userPersistenceOutPort, never()).saveUser(any());
    }

    @Test
    @DisplayName("Should throw AdminException if user already exists in Keycloak")
    void defaultUserRegistration_userAlreadyExists_throwsException() {
        doNothing().when(customerInputValidator).validate(any(DefaultRegistrationRequest.class));
        when(userRegistrationService.registerUserInKeycloak(any(), anyString()))
                .thenThrow(new AdminNotFoundException(MessageUtil.ADMIN_ALREADY_EXISTS_IN_KEYCLOAK));
        AdminNotFoundException exception = assertThrows(AdminNotFoundException.class, () ->
                customerService.defaultUserRegistration(validDto));
        assertEquals(MessageUtil.ADMIN_ALREADY_EXISTS_IN_KEYCLOAK, exception.getMessage());
        verify(userPersistenceOutPort, never()).saveUser(any());
    }

    @Test
    @DisplayName("Should throw AdminException if Keycloak user creation fails")
    void defaultUserRegistration_keycloakCreationFails_throwsException() {
        doNothing().when(customerInputValidator).validate(any(DefaultRegistrationRequest.class));
        when(userRegistrationService.registerUserInKeycloak(any(), anyString()))
                .thenThrow(new AdminNotFoundException(MessageUtil.KEYCLOAK_CREATION_FAILED));
        AdminNotFoundException exception = assertThrows(AdminNotFoundException.class, () ->
                customerService.defaultUserRegistration(validDto));
        assertEquals(MessageUtil.KEYCLOAK_CREATION_FAILED, exception.getMessage());
        verify(userPersistenceOutPort, never()).saveUser(any());
    }
}
