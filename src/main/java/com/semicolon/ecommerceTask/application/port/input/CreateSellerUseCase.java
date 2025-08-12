package com.semicolon.ecommerceTask.application.port.input;

import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.SellerRegistrationFormDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.sellerRegistrationResponse.ActionOnSellerFormResponseDto;

public interface CreateSellerUseCase {
    ActionOnSellerFormResponseDto requestSellerRegistration(SellerRegistrationFormDto registrationDto);
}