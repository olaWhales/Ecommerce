package com.semicolon.ecommerceTask.infrastructure.adapter.controllers;

import com.semicolon.ecommerceTask.application.port.input.ApproveSellerUseCase;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
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
    private final ApproveSellerUseCase approveSellerUseCase;

    @PostMapping("/actions-on-seller-registration/{registrationId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
    public ResponseEntity<ActionOnSellerApprovalResponse> handleSellerRegistration(
            @PathVariable UUID registrationId,
            @RequestBody ActionOnSellerApprovalRequest request) {

        try {
            UserDomainObject result = approveSellerUseCase.approveSeller(registrationId, request);
            if (result != null) {
                return ResponseEntity.ok(ActionOnSellerApprovalResponse.builder()
                    .approved(true)
                    .user(result)
                    .message("Seller registration approved successfully")
                    .build());
            } else {
                return ResponseEntity.ok(ActionOnSellerApprovalResponse.builder()
                    .approved(false)
                    .user(null)
                    .message("Seller registration rejected successfully")
                    .build());
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ActionOnSellerApprovalResponse.builder()
                    .approved(false)
                    .user(null)
                    .message("Error: " + e.getMessage())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ActionOnSellerApprovalResponse.builder()
                    .approved(false)
                    .user(null)
                    .message("An error occurred: " + e.getMessage())
                    .build());
        }
    }
}