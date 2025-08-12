package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductUpdateDto {
    private String name;
    private String description;
    private BigDecimal price;
    private int inStockQuantity;
    private String imageUrl;
}