package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "pending_seller_registrations")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PendingSellerRegistrationEntity {
    @Id
    @UuidGenerator
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String customerEmail;

    // --- You were missing this field ---
    private String businessName;

    private String details;

    @Column(nullable = false)
    private LocalDateTime submissionDate;
}