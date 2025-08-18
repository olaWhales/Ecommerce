package com.semicolon.ecommerceTask.application.port.input;

import com.semicolon.ecommerceTask.domain.model.SellerFormSubmissionDomain;

public interface SellerUseCase {

    SellerFormSubmissionDomain requestSellerRegistration(SellerFormSubmissionDomain registrationDto);
//    SellerFormSubmissionResponse requestSellerRegistration(SellerFormSubmissionDomain registrationDto);
}
