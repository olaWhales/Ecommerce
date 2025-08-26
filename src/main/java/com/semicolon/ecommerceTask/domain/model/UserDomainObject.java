package com.semicolon.ecommerceTask.domain.model;

import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.enumPackages.UserRole;
import lombok.*;

import java.util.List;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDomainObject {
    private String id;
    private String firstName;
    private String lastName;
    private String username ;
    private String email;
    private String password;
    private List<UserRole> roles;

}
