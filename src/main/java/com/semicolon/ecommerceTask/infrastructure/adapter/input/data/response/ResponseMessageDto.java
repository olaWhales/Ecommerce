package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessageDto {
    private String message;
    private String token;
    private String email;
}
