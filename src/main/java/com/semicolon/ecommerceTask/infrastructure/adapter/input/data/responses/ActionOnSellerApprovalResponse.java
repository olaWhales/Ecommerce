package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.responses;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ActionOnSellerApprovalResponse {
    private boolean approved;
    private String message;
}
