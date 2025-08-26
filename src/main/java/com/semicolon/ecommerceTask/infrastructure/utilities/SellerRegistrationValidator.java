package com.semicolon.ecommerceTask.infrastructure.utilities;

import com.semicolon.ecommerceTask.application.port.output.persistence.SellerFormSubmissionPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.exception.ValidationException;
import com.semicolon.ecommerceTask.domain.model.SellerFormSubmissionDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SellerRegistrationValidator {

    private final SellerFormSubmissionPersistenceOutPort sellerFormSubmissionPersistenceOutPort;

    public void validateEmailAndDuplication(String email) {
        ValidationUtil.validateEmail(email);
        Optional<SellerFormSubmissionDomain> existing = sellerFormSubmissionPersistenceOutPort.findByEmail(email);
        if (existing.isPresent()) {
            throw new ValidationException(MessageUtil.A_SELLER_REGISTRATION_REQUEST_IS_ALREADY_PENDING.formatted(email)
            );
        }
    }
    public void validateUserId(String keycloakUserId) {
        if (keycloakUserId == null || keycloakUserId.trim().isEmpty()) {
            throw new ValidationException(MessageUtil.AUTHENTICATED_USER_ID_MISSING);
        }
    }
}
