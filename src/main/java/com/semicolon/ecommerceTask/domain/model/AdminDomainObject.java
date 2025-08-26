package com.semicolon.ecommerceTask.domain.model;

import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.enumPackages.UserRole;
import lombok.*;

import java.util.List;

@Builder(toBuilder = true)
@Getter
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

