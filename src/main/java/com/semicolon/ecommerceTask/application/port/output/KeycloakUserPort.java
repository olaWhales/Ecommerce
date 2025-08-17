package com.semicolon.ecommerceTask.application.port.output;

import com.semicolon.ecommerceTask.domain.model.UserDomainObject;

public interface KeycloakUserPort {
    String createUser(UserDomainObject userDomainObject);
}
