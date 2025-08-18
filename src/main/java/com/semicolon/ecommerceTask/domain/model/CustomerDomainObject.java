package com.semicolon.ecommerceTask.domain.model;

import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entities.enumPackage.UserRole;
import lombok.*;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDomainObject {
    private String id;
    private String keycloakId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<UserRole> roles;
}