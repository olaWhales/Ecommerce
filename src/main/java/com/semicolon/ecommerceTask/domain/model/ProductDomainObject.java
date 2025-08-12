package com.semicolon.ecommerceTask.domain.model;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
public class ProductDomainObject {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private int inStockQuantity;
    private UUID sellerId;
    private String imageUrl;
}