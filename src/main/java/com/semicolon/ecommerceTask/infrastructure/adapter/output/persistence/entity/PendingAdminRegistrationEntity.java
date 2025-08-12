package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private String token;
    private LocalDateTime expiration;
}