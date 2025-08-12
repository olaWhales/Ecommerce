package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response;

import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ActionOnSellerApprovalResponse {
    private boolean approved;
    private UserDomainObject user;
    private String message;
}
