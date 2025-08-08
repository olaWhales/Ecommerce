package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSuperAdminRequest {
    private String username;
    private String email;
    private String password;

}
