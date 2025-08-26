package com.semicolon.ecommerceTask.domain.model;

import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.enumPackages.CartStatus;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDomainObject {
    private UUID id;
    private UserDomainObject user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private CartStatus status;


}
