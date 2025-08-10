package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateSuperAdminResponse {
    private String userId;
    private String username;
    private String email;
    private String role;
}
