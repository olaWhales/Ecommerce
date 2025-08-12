package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.userEntity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pending_seller_registration")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerFormSubmissionEntity {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String customerEmail;

    @Column(nullable = false)
    private String businessName;

    private String details;

    private LocalDateTime submissionDate;

    private String keycloakUserId;

//    @Version
//    private Long version;
}
