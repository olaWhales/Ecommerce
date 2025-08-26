package com.semicolon.ecommerceTask.infrastructure.input.data.requests;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCartItemQuantityRequest {
    private int quantity;
}