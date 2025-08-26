package com.semicolon.ecommerceTask.domain.model;

import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.enumPackages.UserRole;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellerDomainObject {
    private String id;
    private String keycloakId;
    private String email;
    private String details;
    private List<UserRole> roles;


}