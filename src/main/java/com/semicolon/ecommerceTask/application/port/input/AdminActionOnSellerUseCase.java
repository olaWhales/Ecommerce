package com.semicolon.ecommerceTask.application.port.input;

import com.semicolon.ecommerceTask.domain.model.ActionOnSellerApproveDomainObject;

import java.util.UUID;

public interface AdminActionOnSellerUseCase {
    String approveSeller(UUID registrationId, ActionOnSellerApproveDomainObject approvalAction);
}

