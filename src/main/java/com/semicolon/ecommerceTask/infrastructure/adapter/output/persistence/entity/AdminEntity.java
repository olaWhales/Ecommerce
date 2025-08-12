package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity;

import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.enumPackage.UserRole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
public class AdminEntity {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;
    private String keycloakId;
    private String email;
    private String firstName;
    private String lastName;
    @ElementCollection
    private List<String> roles;

    @Getter
    @Setter
    @Builder
    public static class PendingRegistrationDomainObject {
    //    private String email;
    //    private String firstName;
    //    private String lastName;
    //    private String token;
    //    private LocalDateTime expiration;
        private UUID id;
        private String firstName;
        private String lastName;
        private String email;
        private String password; // <-- New password field added here
        private UserRole role;
        private String details;
        private LocalDateTime createdDate;
    }
}