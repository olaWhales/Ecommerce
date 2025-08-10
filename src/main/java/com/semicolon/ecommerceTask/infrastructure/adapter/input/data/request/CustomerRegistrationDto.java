package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerRegistrationDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}