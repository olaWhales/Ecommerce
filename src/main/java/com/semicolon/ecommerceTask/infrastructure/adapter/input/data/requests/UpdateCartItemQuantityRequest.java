package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.requests;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCartItemQuantityRequest {
    private int quantity;
}