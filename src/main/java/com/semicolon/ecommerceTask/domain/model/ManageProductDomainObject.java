package com.semicolon.ecommerceTask.domain.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
public class ManageProductDomainObject {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private int inStockQuantity;
    private String sellerId;
    private String imageUrl;
    private CategoryDomainObject categoryDomainObject;
}


