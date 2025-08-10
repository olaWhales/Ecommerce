package com.semicolon.ecommerceTask.domain.service;

import com.semicolon.ecommerceTask.application.port.input.CreateCustomerUseCase;
import com.semicolon.ecommerceTask.application.port.output.KeycloakAdminOutPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.CustomerPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.exception.ValidationException;
import com.semicolon.ecommerceTask.domain.model.CustomerDomainObject;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.CustomerRegistrationDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.CustomerResponseDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.CustomerMapper;
import com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateCustomerService implements CreateCustomerUseCase {

    private final UserRegistrationService userRegistrationService;
    private final KeycloakAdminOutPort keycloakAdminOutPort;
    private final CustomerPersistenceOutPort customerPersistenceOutPort;
    private final PasswordEncoder passwordEncoder;
    private final CustomerMapper customerMapper;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*?])(?=\\S+$).{8,}$");

    @Transactional
    @Override
    public CustomerResponseDto registerCustomer(CustomerRegistrationDto dto) {
        validateRegistrationInput(dto);

        // Map DTO to generic UserDomainObject
        UserDomainObject user = UserDomainObject.builder()
            .email(dto.getEmail())
            .firstName(dto.getFirstName())
            .lastName(dto.getLastName())
            .build();

        // Delegate core registration to the generic service
        String keycloakId = userRegistrationService.registerUserInKeycloak(user, dto.getPassword());

        // Customer-specific logic: assign the default BUYER role
        keycloakAdminOutPort.assignRealmRoles(keycloakId, Collections.singletonList("BUYER"));

        // Save to local customer database
        CustomerDomainObject customer = CustomerDomainObject.builder()
                .keycloakId(keycloakId)
                .email(dto.getEmail())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .password(passwordEncoder.encode(dto.getPassword()))
                .roles(Collections.singletonList("BUYER"))
                .build();
        customerPersistenceOutPort.saveCustomer(customer);
        return customerMapper.toResponseDto(customer);
    }

    private void validateRegistrationInput(CustomerRegistrationDto dto) {
        if (dto.getFirstName() == null || dto.getFirstName().trim().isEmpty()) {
            throw new ValidationException(MessageUtil.FIRST_NAME_REQUIRED);
        }
        if (dto.getLastName() == null || dto.getLastName().trim().isEmpty()) {
            throw new ValidationException(MessageUtil.LAST_NAME_REQUIRED);
        }
        if (dto.getEmail() == null || !EMAIL_PATTERN.matcher(dto.getEmail()).matches()) {
            throw new ValidationException(MessageUtil.INVALID_EMAIL);
        }
        if (dto.getPassword() == null || !PASSWORD_PATTERN.matcher(dto.getPassword()).matches()) {
            throw new ValidationException(MessageUtil.INVALID_PASSWORD);
        }
    }
}