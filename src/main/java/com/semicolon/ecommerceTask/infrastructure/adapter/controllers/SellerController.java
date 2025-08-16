package com.semicolon.ecommerceTask.infrastructure.adapter.controllers;

import com.semicolon.ecommerceTask.application.port.input.CreateSellerUseCase;
import com.semicolon.ecommerceTask.domain.model.SellerFormSubmissionDomain;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.SellerRegistrationFormDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.sellerRegistrationResponse.ActionOnSellerFormResponseDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserEntity;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.SellerFormSubmissionMapper;
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
    private final CreateSellerUseCase createSellerUseCase;
    private final SellerFormSubmissionMapper sellerFormSubmissionMapper;

    @PostMapping("/request-registration")
    public ResponseEntity<ActionOnSellerFormResponseDto> requestSellerRegistration(@AuthenticationPrincipal UserDomainObject user, @Valid @RequestBody SellerRegistrationFormDto dto) {
//        log.info("Authenticated user =======.>>>>> {}",user);
        SellerFormSubmissionDomain sellerRegistrationFormDto = sellerFormSubmissionMapper.toDomainFromDto(dto);
        ActionOnSellerFormResponseDto response = createSellerUseCase.requestSellerRegistration(sellerRegistrationFormDto);
        return ResponseEntity.ok(response);
    }
}
