package com.semicolon.ecommerceTask.infrastructure.input.data.responses.orderResponse;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class OrderItemResponse {
    private UUID id;
    private UUID productId;
    private BigDecimal unitPrice;
    private int quantity;
}
