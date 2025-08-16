package com.semicolon.ecommerceTask.domain.model;

import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.enumPackage.UserRole;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminDomainObject {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private List<UserRole> roles;
}

