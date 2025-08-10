package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "pending_registrations") // Ensure table name is specified
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PendingRegistrationEntity {
    @Id
    @Column(name = "email", length = 255, nullable = false, updatable = false)
    private String email;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "expiration", nullable = false)
    private LocalDateTime expiration;
}