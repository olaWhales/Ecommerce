package com.semicolon.ecommerceTask.domain.services;

import com.semicolon.ecommerceTask.application.port.input.SellerUseCase;
import com.semicolon.ecommerceTask.application.port.output.persistence.SellerFormSubmissionPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.SellerFormSubmissionDomain;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.mapper.SellerFormSubmissionMapper;
import com.semicolon.ecommerceTask.infrastructure.utilities.AuthenticationExtractor;
import com.semicolon.ecommerceTask.infrastructure.utilities.SellerRegistrationValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SellerSubmissionService implements SellerUseCase {

    private final SellerFormSubmissionPersistenceOutPort sellerFormSubmissionPersistenceOutPort;
    private final SellerFormSubmissionMapper sellerFormSubmissionMapper;
    private final SellerRegistrationValidator sellerRegistrationValidator;
    private final AuthenticationExtractor authenticationExtractor;

    @Transactional
    @Override
    public SellerFormSubmissionDomain requestSellerRegistration(SellerFormSubmissionDomain registrationRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String customerEmail = authenticationExtractor.extractCustomerEmail(authentication);
        sellerRegistrationValidator.validateEmailAndDuplication(customerEmail);
        String keycloakUserId = authenticationExtractor.extractKeycloakUserId(authentication);
        sellerRegistrationValidator.validateUserId(keycloakUserId);
        SellerFormSubmissionDomain pendingRegistration =
                sellerFormSubmissionMapper.enrichDomainFromTemplate(
                        registrationRequest, customerEmail, keycloakUserId
                );
        return sellerFormSubmissionPersistenceOutPort.savePendingRegistration(pendingRegistration);
    }
}

