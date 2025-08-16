package com.semicolon.ecommerceTask.domain.model;

import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.enumPackage.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
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