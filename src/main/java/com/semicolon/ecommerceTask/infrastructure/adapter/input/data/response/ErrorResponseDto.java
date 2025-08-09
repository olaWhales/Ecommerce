package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDto {
    private String message;
    private int status;
    private String timestamp;
}
