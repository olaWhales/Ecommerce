package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class ProductResponseDto {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private int inStockQuantity;
    private String imageUrl;
    private CategoryResponseDto category;

}
