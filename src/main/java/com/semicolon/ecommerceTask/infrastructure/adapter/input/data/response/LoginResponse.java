package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token ;
    private String message ;
}
