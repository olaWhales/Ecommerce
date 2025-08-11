package com.semicolon.ecommerceTask.domain.service;

import com.semicolon.ecommerceTask.application.port.input.CreateSellerUseCase;
import com.semicolon.ecommerceTask.application.port.input.PendingSellerRegistrationOutPort;
import com.semicolon.ecommerceTask.application.port.output.KeycloakAdminOutPort;
import com.semicolon.ecommerceTask.domain.exception.ValidationException;
import com.semicolon.ecommerceTask.domain.model.PendingSellerRegistration;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.SellerRegistrationFormDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil.A_SELLER_REGISTRATION_REQUEST_IS_ALREADY_PENDING;
import static com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil.SELLER_REGISTRATION_REQUEST_SUBMITTED_SUCCESSFULLY;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateSellerService implements CreateSellerUseCase {

    private final PendingSellerRegistrationOutPort pendingSellerRegistrationOutPort;
    private final KeycloakAdminOutPort keycloakAdminOutPort;

    @Transactional
    @Override
    public String requestSellerRegistration(SellerRegistrationFormDto registrationDto) {
        String customerEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (pendingSellerRegistrationOutPort.findByEmail(customerEmail).isPresent()) {
            return A_SELLER_REGISTRATION_REQUEST_IS_ALREADY_PENDING.formatted(customerEmail);
        }

        // This check is the source of your error. The user from the token does not exist in Keycloak.
        if (keycloakAdminOutPort.findUserByEmail(customerEmail).isEmpty()) {
            throw new ValidationException(MessageUtil.CUSTOMER_NOT_FOUND.formatted(customerEmail));
        }

        PendingSellerRegistration pendingRegistration = PendingSellerRegistration.builder()
            .customerEmail(customerEmail)
            .businessName(registrationDto.getName())
            .details(registrationDto.getDetails())
            .submissionDate(LocalDateTime.now())
            .build();

        pendingSellerRegistrationOutPort.savePendingRegistration(pendingRegistration);
        return SELLER_REGISTRATION_REQUEST_SUBMITTED_SUCCESSFULLY.formatted(customerEmail);
    }
}