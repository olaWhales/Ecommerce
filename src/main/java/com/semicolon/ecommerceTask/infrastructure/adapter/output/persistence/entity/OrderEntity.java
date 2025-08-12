package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity;

import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.enumPackage.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne
    private UserEntity userEntity;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal totalAmount;

    @NotNull
    private LocalDateTime dateCreated;

}
