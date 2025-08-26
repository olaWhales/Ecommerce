package com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.persistenceEntities;

import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.enumPackages.CartStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartEntity {
    @Id
    @UuidGenerator
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne
    private UserEntity user;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @NotNull
    @Enumerated
    private CartStatus status;
}
