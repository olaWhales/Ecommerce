package com.semicolon.ecommerceTask.application.port.input;

import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.ActionOnSellerApprovalRequest;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.ActionOnSellerApprovalResponse;

import java.util.UUID;

public interface AdminActionOnSellerUseCase {
    ActionOnSellerApprovalResponse approveSeller(UUID registrationId, ActionOnSellerApprovalRequest approvalAction);
}