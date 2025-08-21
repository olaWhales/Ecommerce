package com.semicolon.ecommerceTask.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
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


