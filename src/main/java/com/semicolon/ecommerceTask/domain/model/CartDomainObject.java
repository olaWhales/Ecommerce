package com.semicolon.ecommerceTask.domain.model;

import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.enumPackage.CartStatus;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartDomainObject {
    private UUID id;
    private User user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private CartStatus cartStatus;
}
