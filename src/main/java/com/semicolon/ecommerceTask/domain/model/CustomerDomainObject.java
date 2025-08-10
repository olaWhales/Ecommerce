package com.semicolon.ecommerceTask.domain.model;

import lombok.*;
import java.util.List;

@Getter
@Setter
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
    private List<String> roles;
}