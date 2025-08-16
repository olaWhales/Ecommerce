package com.semicolon.ecommerceTask.domain.service;

import com.semicolon.ecommerceTask.application.port.input.CreateSellerUseCase;
import com.semicolon.ecommerceTask.application.port.output.persistence.SellerFormSubmissionPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.exception.ValidationException;
import com.semicolon.ecommerceTask.domain.model.SellerFormSubmissionDomain;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.sellerRegistrationResponse.ActionOnSellerFormResponseDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.SellerFormSubmissionMapper;
import com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil;
import com.semicolon.ecommerceTask.infrastructure.adapter.utilities.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateSellerSubmissionService implements CreateSellerUseCase {

    private final SellerFormSubmissionPersistenceOutPort sellerFormSubmissionPersistenceOutPort;
    private final SellerFormSubmissionMapper sellerFormSubmissionMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    @Override
    public ActionOnSellerFormResponseDto requestSellerRegistration(SellerFormSubmissionDomain registrationDto) {
//        log.info("Processing seller registration request with details: {}", registrationDto.getDetails());

        // Get authentication and log details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        log.debug("Authentication details: principal={}, name={}", authentication.getPrincipal(), authentication.getName());
        if (authentication.getPrincipal() instanceof Jwt jwt) {
//            log.debug("JWT claims: {}", jwt.getClaims());
        }

        // Extract and validate email
        String customerEmail = extractCustomerEmail(authentication);
//        log.info("Authenticated user email: {}", customerEmail);
        ValidationUtil.validateEmail(customerEmail);

        // Check for existing pending registration
        Optional<SellerFormSubmissionDomain> existingRegistration = sellerFormSubmissionPersistenceOutPort.findByEmail(customerEmail);
        if (existingRegistration.isPresent()) {
//            log.error("A seller registration request is already pending for email: {}", customerEmail);
            throw new ValidationException(MessageUtil.A_SELLER_REGISTRATION_REQUEST_IS_ALREADY_PENDING.formatted(customerEmail));
        }

        // Extract Keycloak user ID
        String keycloakUserId = extractKeycloakUserId(authentication);
//        log.info("Extracted Keycloak user ID: {}", keycloakUserId);

        // Create and save seller registration
//        UUID registrationId = UUID.randomUUID();
        SellerFormSubmissionDomain pendingRegistration = sellerFormSubmissionMapper.enrichDomainFromTemplate(
                registrationDto, customerEmail, keycloakUserId
        );
        SellerFormSubmissionDomain savedRegistration = sellerFormSubmissionPersistenceOutPort.savePendingRegistration(pendingRegistration);
//        log.info("Saved seller registration for email: {}, registration ID: {}", customerEmail);

        // Build and return response
        ActionOnSellerFormResponseDto response = ActionOnSellerFormResponseDto.builder()
                .message(MessageUtil.SELLER_REGISTRATION_REQUEST_SUBMITTED_SUCCESSFULLY.formatted(customerEmail))
                .registrationId(savedRegistration.getId())
                .keycloakUserId(keycloakUserId)
                .build();
        log.info("Seller registration response created for email: {}", customerEmail);
        return response;
    }

    private String extractCustomerEmail(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof Jwt jwt) {
            String email = jwt.getClaimAsString("email");
            if (email == null || email.trim().isEmpty()) {
                log.error("Email claim is missing in JWT for principal: {}", principal);
                throw new ValidationException(MessageUtil.AUTHENTICATED_USER_EMAIL_MISSING);
            }
            log.debug("Extracted email from JWT: {}", email);
            return email;
        } else if (principal instanceof UserDomainObject user) {
            String email = user.getEmail();
            if (email == null || email.trim().isEmpty()) {
                log.error("Email is missing in UserDomainObject for principal: {}", principal);
                throw new ValidationException(MessageUtil.AUTHENTICATED_USER_EMAIL_MISSING);
            }
            log.debug("Extracted email from UserDomainObject: {}", email);
            return email;
        }
        log.error("Authentication principal is neither a JWT nor a UserDomainObject: {}", principal);
        throw new ValidationException(MessageUtil.INVALID_AUTHENTICATION_PRINCIPAL);
    }

    private String extractKeycloakUserId(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof Jwt jwt) {
            String keycloakUserId = jwt.getSubject();
            if (keycloakUserId == null || keycloakUserId.isEmpty()) {
                log.error("Keycloak user ID (sub) is missing in JWT for principal: {}", principal);
                throw new ValidationException(MessageUtil.AUTHENTICATED_USER_ID_MISSING);
            }
            log.debug("Extracted Keycloak user ID from JWT: {}", keycloakUserId);
            return keycloakUserId;
        } else if (principal instanceof UserDomainObject user) {
            String keycloakUserId = user.getId();
            if (keycloakUserId == null || keycloakUserId.isEmpty()) {
                log.error("Keycloak user ID is missing in UserDomainObject for principal: {}", principal);
                throw new ValidationException(MessageUtil.AUTHENTICATED_USER_ID_MISSING);
            }
            log.debug("Extracted Keycloak user ID from UserDomainObject: {}", keycloakUserId);
            return keycloakUserId;
        }
        log.error("Authentication principal is neither a JWT nor a UserDomainObject: {}", principal);
        throw new ValidationException(MessageUtil.INVALID_AUTHENTICATION_PRINCIPAL);
    }
}