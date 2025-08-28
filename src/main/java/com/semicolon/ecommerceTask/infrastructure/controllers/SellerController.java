package com.semicolon.ecommerceTask.infrastructure.controllers;

import com.semicolon.ecommerceTask.application.port.input.SellerUseCase;
import com.semicolon.ecommerceTask.domain.model.SellerFormSubmissionDomain;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.input.data.requests.SellerRegistrationFormRequest;
import com.semicolon.ecommerceTask.infrastructure.input.data.responses.sellerRegistrationResponse.SellerFormSubmissionResponse;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.mapper.SellerFormSubmissionMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
@Slf4j
public class SellerController {
    private final SellerUseCase sellerUseCase;
    private final SellerFormSubmissionMapper sellerFormSubmissionMapper;

    @PostMapping("/request-registration")
    public ResponseEntity<SellerFormSubmissionResponse> requestSellerRegistration(@AuthenticationPrincipal UserDomainObject user,
                                                                                  @Valid @RequestBody SellerRegistrationFormRequest dto) {
        SellerFormSubmissionDomain sellerRegistrationFormDto = sellerFormSubmissionMapper.toDomainFromDto(dto);
        SellerFormSubmissionDomain savedDomain = sellerUseCase.requestSellerRegistration(sellerRegistrationFormDto);
        SellerFormSubmissionResponse response = sellerFormSubmissionMapper.toResponse(savedDomain);
        return ResponseEntity.ok(response);
    }
}
