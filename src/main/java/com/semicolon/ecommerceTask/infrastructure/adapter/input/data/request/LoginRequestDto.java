package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    private String username ;
    private String password ;
}
