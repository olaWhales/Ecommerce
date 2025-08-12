package com.semicolon.ecommerceTask.domain.model;

import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.enumPackage.UserRole;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserDomainObject {
    private String firstName;
    private String lastName;
    private String username ;
    private String email;
    private String password;
    private List<UserRole> roles;
    private String keycloakId ;
}
