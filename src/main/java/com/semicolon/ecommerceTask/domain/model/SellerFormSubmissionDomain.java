package com.semicolon.ecommerceTask.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellerFormSubmissionDomain {
    private UUID id;
    private String customerEmail;
    private String businessName;
    private String details;
    private LocalDateTime submissionDate;
    private String keycloakUserId;
    @Builder.Default
    private Long version = 0L;


}
