package com.semicolon.ecommerceTask.domain.model;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDomainObject {
    private UUID id;
    private UUID cartId;
    private UUID productId;
    private int quantity ;
}

