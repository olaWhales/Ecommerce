package com.semicolon.ecommerceTask.infrastructure.input.data.requests;

import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCartItemQuantityRequest {
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
}