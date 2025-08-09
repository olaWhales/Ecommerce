package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private String token ;
    private String message ;
}
