package com.semicolon.ecommerceTask.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemModel {
    @Id
    private UUID id;

    @NotNull
    @ManyToOne
    private OrderDomainObject orderEntity;

    @NotNull
    @ManyToOne
    private ProductDomainObject productEntity;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal unitPrice;

    @Min(1)
    private int quantity;

}
