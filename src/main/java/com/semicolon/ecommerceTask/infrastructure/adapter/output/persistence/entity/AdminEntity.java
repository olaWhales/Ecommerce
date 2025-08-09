package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "admins")
@Data
@Builder
public class AdminEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;
    private String keycloakId;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    @ElementCollection
    private List<String> roles;
}