package com.semicolon.ecommerceTask.domain.model;

import lombok.*;

import java.util.UUID;
import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDomainObject {
    private UUID id;
    private UUID cartEntityId;
    private UUID productId;
    private int quantity ;
    private BigDecimal unitPrice;

}

