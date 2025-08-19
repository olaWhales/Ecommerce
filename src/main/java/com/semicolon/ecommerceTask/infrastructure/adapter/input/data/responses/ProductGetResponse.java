package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductGetResponse {
    private String name;
    private String description;
    private BigDecimal price;
    private int inStockQuantity;
    private String imageUrl;
    private CategoryResponseDto category;
}