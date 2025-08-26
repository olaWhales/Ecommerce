package com.semicolon.ecommerceTask.infrastructure.input.data.responses.sellerRegistrationResponse;

import lombok.Builder;
import lombok.Getter;
import java.util.UUID;

@Getter
@Builder
public class SellerFormSubmissionResponse {
    private String message;
    private UUID registrationId;
    private String keycloakUserId;


}
