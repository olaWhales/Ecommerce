package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.sellerRegistrationResponse;

import lombok.Builder;
import lombok.Getter;
import java.util.UUID;

@Getter
@Builder
public class ActionOnSellerFormResponseDto {
    private String message;
    private UUID registrationId;
    private String keycloakUserId;
}