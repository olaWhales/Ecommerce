package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRegResponse {
    private String message;
    private String name;
    private String description;
    private BigDecimal price;
    private int inStockQuantity;
}
