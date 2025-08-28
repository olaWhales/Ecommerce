package com.semicolon.ecommerceTask.infrastructure.input.data.responses.orderResponse;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class OrderResponse {
    private UUID id;
    private UUID userId;
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime dateCreated;
    private List<OrderItemResponse> items;
}
