package com.semicolon.ecommerceTask.domain.services;

import com.semicolon.ecommerceTask.application.port.input.CustomerUseCase;
import com.semicolon.ecommerceTask.application.port.output.KeycloakAdminOutPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.CustomerPersistenceOutPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.UserPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.input.data.requests.DefaultRegistrationRequest;
import com.semicolon.ecommerceTask.infrastructure.input.data.responses.UserDomainObjectResponse;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.enumPackages.UserRole;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.mapper.CustomerMapper;
import com.semicolon.ecommerceTask.infrastructure.utilities.CustomerInputValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService implements CustomerUseCase {

    private final UserRegistrationService userRegistrationService;
    private final KeycloakAdminOutPort keycloakAdminOutPort;
    private final UserPersistenceOutPort userPersistenceOutPort;
    private final CustomerPersistenceOutPort customerPersistenceOutPort;
    private final PasswordEncoder passwordEncoder;
    private final CustomerMapper customerMapper;
    private final CustomerInputValidator customerInputValidator;

    @Transactional
    @Override
    public UserDomainObjectResponse defaultUserRegistration(DefaultRegistrationRequest dto) {
        customerInputValidator.validate(dto);
        UserDomainObject user = UserDomainObject.builder()
            .email(dto.getEmail())
            .firstName(dto.getFirstName())
            .lastName(dto.getLastName())
            .build();
        String keycloakId = userRegistrationService.registerUserInKeycloak(user, dto.getPassword());
        keycloakAdminOutPort.assignRealmRoles(keycloakId, Collections.singletonList(UserRole.BUYER));

        UserDomainObject customer = UserDomainObject.builder()
            .id(keycloakId)
            .email(dto.getEmail())
            .firstName(dto.getFirstName())
            .lastName(dto.getLastName())
            .password(passwordEncoder.encode(dto.getPassword()))
            .roles(Collections.singletonList(UserRole.BUYER))
            .build();
        UserDomainObject savedCustomer = userPersistenceOutPort.saveUser(customer);
        return customerMapper.toResponseDto(savedCustomer);
    }

}