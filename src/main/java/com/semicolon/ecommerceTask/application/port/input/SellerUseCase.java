package com.semicolon.ecommerceTask.application.port.input;

import com.semicolon.ecommerceTask.domain.model.SellerFormSubmissionDomain;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.sellerRegistrationResponse.SellerFormSubmissionResponse;

public interface SellerUseCase {

    SellerFormSubmissionDomain requestSellerRegistration(SellerFormSubmissionDomain registrationDto);
//    SellerFormSubmissionResponse requestSellerRegistration(SellerFormSubmissionDomain registrationDto);
}
