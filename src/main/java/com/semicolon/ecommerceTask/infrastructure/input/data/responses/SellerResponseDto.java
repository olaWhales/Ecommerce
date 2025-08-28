package com.semicolon.ecommerceTask.infrastructure.input.data.responses;

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