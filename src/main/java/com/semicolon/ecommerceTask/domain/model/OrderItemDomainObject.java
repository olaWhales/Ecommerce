package com.semicolon.ecommerceTask.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDomainObject {
    private UUID id;
    private OrderDomainObject orderEntity;
    private ManageProductDomainObject productEntity;
    private BigDecimal unitPrice;
    private int quantity;


}
