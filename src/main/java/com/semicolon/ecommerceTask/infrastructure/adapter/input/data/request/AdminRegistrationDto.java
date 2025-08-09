package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request;

import lombok.Data;

@Data
public class AdminRegistrationDto {
    private String token;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}