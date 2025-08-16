package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
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
public class ProductEntity {
    @Id
    @UuidGenerator
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    @NotBlank
    @Column(length = 100)
    private String name;

    @NotBlank
    @Column(length = 1000)
    private String description;

    @NotNull
    private BigDecimal price;

    @NotNull
    private int inStockQuantity;

    private String sellerId;

    @NotBlank
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @NotNull
    private CategoryEntity categoryEntity;
}
