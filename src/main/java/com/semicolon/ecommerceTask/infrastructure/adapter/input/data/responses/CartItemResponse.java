package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.responses;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {
    private UUID id;
    private UUID cartEntityId;
    private UUID productId;
    private int quantity;



}

