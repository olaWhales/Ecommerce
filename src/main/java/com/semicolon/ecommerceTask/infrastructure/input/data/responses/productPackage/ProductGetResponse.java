package com.semicolon.ecommerceTask.infrastructure.input.data.responses.productPackage;

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
    private String category;


}