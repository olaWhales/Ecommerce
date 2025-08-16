package com.semicolon.ecommerceTask.domain.model;

import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.enumPackage.OrderStatus;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDomainObject {
//    @Id
    private UUID id;

    @NotNull
    @ManyToOne
    private UserEntity user;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal totalAmount;

    @NotNull
    private LocalDateTime dateCreated;

}
