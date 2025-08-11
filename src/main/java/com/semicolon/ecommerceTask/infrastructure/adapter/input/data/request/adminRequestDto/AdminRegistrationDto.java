package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.adminRequestDto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminRegistrationDto {
    private String token;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}