package com.semicolon.ecommerceTask.domain.model;

import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entities.UserRole;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PendingRegistrationDomainObject {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String token;
    private LocalDateTime expiration;
    private String password;
    private List<UserRole> role;
    private String details;
    private LocalDateTime createdDate;



}
