package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.requests;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActionOnSellerApprovalRequest {

    @NotNull(message = "Approval decision must be provided")
    private Boolean isApprove;
}
