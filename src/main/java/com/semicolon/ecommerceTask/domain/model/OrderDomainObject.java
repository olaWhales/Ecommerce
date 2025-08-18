package com.semicolon.ecommerceTask.domain.model;

import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entities.enumPackage.OrderStatus;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entities.UserEntity;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDomainObject {
    private UUID id;
    private UserEntity user;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private LocalDateTime dateCreated;

}
