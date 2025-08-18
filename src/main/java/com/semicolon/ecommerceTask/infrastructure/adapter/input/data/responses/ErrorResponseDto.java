package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.responses;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto {
    private String message;
    private int status;
    private String timestamp;
}
