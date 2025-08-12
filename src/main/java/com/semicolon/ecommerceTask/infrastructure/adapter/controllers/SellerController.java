package com.semicolon.ecommerceTask.infrastructure.adapter.controllers;

import com.semicolon.ecommerceTask.application.port.input.CreateSellerUseCase;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.SellerRegistrationFormDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.sellerRegistrationResponse.ActionOnSellerFormResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerController {
    private final CreateSellerUseCase createSellerUseCase;
    @PostMapping("/request-registration")
    public ResponseEntity<ActionOnSellerFormResponseDto> requestSellerRegistration(@RequestBody SellerRegistrationFormDto dto) {
        ActionOnSellerFormResponseDto response = createSellerUseCase.requestSellerRegistration(dto);
        return ResponseEntity.ok(response);
    }
}
