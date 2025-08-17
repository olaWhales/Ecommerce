package com.semicolon.ecommerceTask.application.port.input;

import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.DefaultRegistrationRequest;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.UserDomainObjectResponse;

public interface CustomerUseCase {
    UserDomainObjectResponse registerCustomer(DefaultRegistrationRequest dto);
}