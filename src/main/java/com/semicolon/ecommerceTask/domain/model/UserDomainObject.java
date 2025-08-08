package com.semicolon.ecommerceTask.domain.model;

import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDomainObject {
//    private UUID id;
//    private String keycloakId;
    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private List<UserRole> roles;


}
