package com.semicolon.ecommerceTask.application.port.input;

import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.CustomerRegistrationDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.UserDomainObjectResponse;

public interface CreateCustomerUseCase {
    UserDomainObjectResponse registerCustomer(CustomerRegistrationDto dto);
}