package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.temporaryPendingRegistrationEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pending_registration")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PendingAdminRegistrationEntity {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    private String firstName;

    @Column(nullable = true)
    private String lastName;

    private String token;

    private LocalDateTime expiration;
}
