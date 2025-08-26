package com.semicolon.ecommerceTask.application.port.input;

import com.semicolon.ecommerceTask.infrastructure.input.data.requests.DefaultRegistrationRequest;
import com.semicolon.ecommerceTask.infrastructure.input.data.responses.UserDomainObjectResponse;

public interface CustomerUseCase {
    UserDomainObjectResponse defaultUserRegistration(DefaultRegistrationRequest dto);
}