package com.semicolon.ecommerceTask.infrastructure.input.data.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartRequest {


    @NotNull(message = "Product ID cannot be null")
    private UUID productId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
}

