package com.semicolon.ecommerceTask.infrastructure.adapter.output.superAdmin;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KeycloakUserDto {
    private String username;
    private String email;
    private String password;
    private String role;
}
