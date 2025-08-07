package com.semicolon.ecommerceTask.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemModel {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne
    private OrderModel orderModel;

    @NotNull
    @ManyToOne
    private ProductModel productModel;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal unitPrice;

    @Min(1)
    private int quantity;

}
