package com.semicolon.ecommerceTask.domain.model;

import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.enumPackages.OrderStatus;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.persistenceEntities.UserEntity;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDomainObject {
    private UUID id;
    private UUID user;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private LocalDateTime dateCreated;
    private List<OrderItemDomainObject> items;
}
