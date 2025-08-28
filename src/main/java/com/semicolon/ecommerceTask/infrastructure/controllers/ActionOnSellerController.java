package com.semicolon.ecommerceTask.infrastructure.controllers;

import com.semicolon.ecommerceTask.application.port.input.AdminActionOnSellerUseCase;
import com.semicolon.ecommerceTask.domain.model.ActionOnSellerApproveDomainObject;
import com.semicolon.ecommerceTask.infrastructure.input.data.requests.ActionOnSellerApprovalRequest;
import com.semicolon.ecommerceTask.infrastructure.input.data.responses.ActionOnSellerApprovalResponse;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.mapper.AdminActionOnSellerFormMapper;
import jakarta.validation.Valid;
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
    private final AdminActionOnSellerFormMapper mapper;

    @PostMapping("/seller-registration/{registrationId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
    public ResponseEntity<ActionOnSellerApprovalResponse> handleSellerRegistration(
            @PathVariable UUID registrationId,
            @Valid @RequestBody ActionOnSellerApprovalRequest request) {
        ActionOnSellerApproveDomainObject domainObject = mapper.toDomain(request);
        String message = adminActionOnSellerUseCase.approveSeller(registrationId, domainObject);
        ActionOnSellerApprovalResponse action = ActionOnSellerApprovalResponse.builder()
            .approved(domainObject.getIsApprove())
            .message(message)
            .build();
        return ResponseEntity.ok(action);
    }
}


