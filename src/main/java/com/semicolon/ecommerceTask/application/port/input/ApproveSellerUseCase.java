package com.semicolon.ecommerceTask.application.port.input;

import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.ActionOnSellerApprovalRequest;

import java.util.UUID;

public interface ApproveSellerUseCase {
    UserDomainObject approveSeller(UUID registrationId, ActionOnSellerApprovalRequest approvalAction);
}