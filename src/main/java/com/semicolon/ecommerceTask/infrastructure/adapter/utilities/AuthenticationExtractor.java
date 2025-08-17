package com.semicolon.ecommerceTask.infrastructure.adapter.utilities;

import com.semicolon.ecommerceTask.domain.exception.ValidationException;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationExtractor {

    public String extractCustomerEmail(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof Jwt jwt) {
            String email = jwt.getClaimAsString("email");
            if (email == null || email.trim().isEmpty()) {throw new ValidationException(MessageUtil.AUTHENTICATED_USER_EMAIL_MISSING);}
            return email;
        } else if (principal instanceof UserDomainObject user) {
            String email = user.getEmail();
            if (email == null || email.trim().isEmpty()) {throw new ValidationException(MessageUtil.AUTHENTICATED_USER_EMAIL_MISSING);}
            return email;
        }
        throw new ValidationException(MessageUtil.INVALID_AUTHENTICATION_PRINCIPAL);
    }

    public String extractKeycloakUserId(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof Jwt jwt) {
            String keycloakUserId = jwt.getSubject();
            if (keycloakUserId == null || keycloakUserId.isEmpty()) {throw new ValidationException(MessageUtil.AUTHENTICATED_USER_ID_MISSING);}
            return keycloakUserId;
        } else if (principal instanceof UserDomainObject user) {
            String keycloakUserId = user.getId();
            if (keycloakUserId == null || keycloakUserId.isEmpty()) {throw new ValidationException(MessageUtil.AUTHENTICATED_USER_ID_MISSING);}
            return keycloakUserId;
        }
        throw new ValidationException(MessageUtil.INVALID_AUTHENTICATION_PRINCIPAL);
    }
}
