package com.semicolon.ecommerceTask.application.port.input;

import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.SellerRegistrationFormDto;

public interface CreateSellerUseCase {
    String requestSellerRegistration(SellerRegistrationFormDto registrationDto);
}