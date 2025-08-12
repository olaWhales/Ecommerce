package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@Table(name = "pending_seller_registration")
@NoArgsConstructor
@AllArgsConstructor
public class SellerFormSubmissionEntity {
    @Id
    private UUID id;
    private String customerEmail;
    private String businessName;
    private String details;
    private LocalDateTime submissionDate;
    private String keycloakUserId;


}