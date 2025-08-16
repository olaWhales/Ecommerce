package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEntity {
    @Id
    @UuidGenerator
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne
    private UserEntity user;

    @NotNull
    @ManyToOne
    private ProductEntity productEntity;

    @Min(1)
    @Max(5)
    private int rating;

    @NotBlank
    private String comment;

    @NotNull
    private LocalDateTime createdAt;

}
