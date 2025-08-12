package com.semicolon.ecommerceTask.domain.service;

import com.semicolon.ecommerceTask.application.port.input.CreateSellerUseCase;
import com.semicolon.ecommerceTask.application.port.input.SellerFormSubmissionOutPort;
import com.semicolon.ecommerceTask.application.port.output.KeycloakAdminOutPort;
import com.semicolon.ecommerceTask.domain.exception.ValidationException;
import com.semicolon.ecommerceTask.domain.model.SellerFormSubmissionDomain;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.SellerRegistrationFormDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.sellerRegistrationResponse.ActionOnSellerFormResponseDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil.A_SELLER_REGISTRATION_REQUEST_IS_ALREADY_PENDING;
import static com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil.SELLER_REGISTRATION_REQUEST_SUBMITTED_SUCCESSFULLY;


@Slf4j
@Service
@RequiredArgsConstructor
public class CreateSellerSubmissionService implements CreateSellerUseCase {
    private final SellerFormSubmissionOutPort sellerFormSubmissionOutPort;
    private final KeycloakAdminOutPort keycloakAdminOutPort;
    @Transactional
    @Override
    public ActionOnSellerFormResponseDto requestSellerRegistration(SellerRegistrationFormDto registrationDto) {
        String customerEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (sellerFormSubmissionOutPort.findByEmail(customerEmail).isPresent()) {
            throw new ValidationException(A_SELLER_REGISTRATION_REQUEST_IS_ALREADY_PENDING.formatted(customerEmail));
        }
// Fetch the user and get Keycloak ID
        UserRepresentation keycloakUser = keycloakAdminOutPort.findUserByEmail(customerEmail)
                .orElseThrow(() -> new ValidationException(MessageUtil.CUSTOMER_NOT_FOUND.formatted(customerEmail)));

        String keycloakUserId = keycloakUser.getId();
        log.info("===> this is keycloak user id {}", keycloakUser.getId());
// Generate UUID for the registration
        UUID registrationId = UUID.randomUUID();
        log.info("===> this is registration user id {}", registrationId);
        SellerFormSubmissionDomain pendingRegistration = SellerFormSubmissionDomain.builder()
                .id(registrationId)
                .customerEmail(customerEmail)
                .businessName(registrationDto.getName())
                .details(registrationDto.getDetails())
                .submissionDate(LocalDateTime.now())
                .keycloakUserId(keycloakUserId)
                .build();
        SellerFormSubmissionDomain savedRegistration = sellerFormSubmissionOutPort.savePendingRegistration(pendingRegistration);
        log.info("===> this is saved registration user {}", savedRegistration.getDetails());

        return ActionOnSellerFormResponseDto.builder()
                .message(SELLER_REGISTRATION_REQUEST_SUBMITTED_SUCCESSFULLY.formatted(customerEmail))
                .registrationId(savedRegistration.getId())
                .keycloakUserId(keycloakUserId)
                .build();
    }
}