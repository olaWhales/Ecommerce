package com.semicolon.ecommerceTask.infrastructure.adapter.controllers;

import com.semicolon.ecommerceTask.application.port.input.AdminActionOnSellerUseCase;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.ActionOnSellerApprovalRequest;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.ActionOnSellerApprovalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ActionOnSellerController {
    private final AdminActionOnSellerUseCase adminActionOnSellerUseCase;

    @PostMapping("/actions-on-seller-registration/{registrationId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
    public ResponseEntity<ActionOnSellerApprovalResponse> handleSellerRegistration(
            @PathVariable UUID registrationId,
            @RequestBody ActionOnSellerApprovalRequest request) {

        ActionOnSellerApprovalResponse result =
                adminActionOnSellerUseCase.approveSeller(registrationId, request);

        return ResponseEntity.ok(result);
    }
}