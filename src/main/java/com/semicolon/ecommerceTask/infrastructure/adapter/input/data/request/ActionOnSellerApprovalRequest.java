package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request;

import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.enumPackage.ApprovalAction;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActionOnSellerApprovalRequest {
    private ApprovalAction action;
}
