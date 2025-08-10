package com.semicolon.ecommerceTask.domain.model;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminDomainObject {
    private UUID id;
    private String keycloakId;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private List<String> roles; // e.g., ["ADMIN"]
}

