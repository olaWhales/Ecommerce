package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private int inStockQuantity;
    private UUID sellerId;
    private String imageUrl;
}