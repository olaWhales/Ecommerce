package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.userEntity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerFormSubmissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "customer_email", nullable = false)
    private String customerEmail;

    @Column(name = "business_name", nullable = false)
    private String businessName;

    @Column(name = "details", columnDefinition = "TEXT")
    private String details;

    @Column(name = "submission_date", nullable = false)
    private LocalDateTime submissionDate;

    @Column(name = "keycloak_user_id", nullable = false)
    private String keycloakUserId;

    @Version
    private Long version; // For optimistic locking
}
