package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.requests;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartRequest {

    private UUID productId;
    private int quantity;

}

