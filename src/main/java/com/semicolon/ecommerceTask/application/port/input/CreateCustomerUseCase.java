package com.semicolon.ecommerceTask.application.port.input;

import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.CustomerRegistrationDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.CustomerResponseDto;

public interface CreateCustomerUseCase {
    CustomerResponseDto registerCustomer(CustomerRegistrationDto dto);
}