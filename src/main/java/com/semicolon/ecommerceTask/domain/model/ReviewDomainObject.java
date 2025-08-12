package com.semicolon.ecommerceTask.domain.model;

import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDomainObject {
    @Id
    private UUID id;

    @NotNull
    @ManyToOne
    private UserEntity user;

    @NotNull
    @ManyToOne
    private ProductDomainObject productEntity;

    @Min(1)
    @Max(5)
    private int rating;

    @NotBlank
    private String comment;

    @NotNull
    private LocalDateTime createdAt;

}
