package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSuperAdminResponse {
    private String userId;
    private String username;
    private String email;
    private String role;
}
