package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellerResponseDto {
    private String id;
    private String email;
    private String details;
}