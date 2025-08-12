package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity;

import com.semicolon.ecommerceTask.domain.model.OrderDomainObject;
import com.semicolon.ecommerceTask.domain.model.ProductDomainObject;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemEntity {
    @Id
    @UuidGenerator
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne
    private OrderEntity orderEntity;

    @NotNull
    @ManyToOne
    private ProductEntity productEntity;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal unitPrice;

    @Min(1)
    private int quantity;

}
