package com.semicolon.ecommerceTask.infrastructure.controllers;

import com.semicolon.ecommerceTask.application.port.input.OrderUseCase;
import com.semicolon.ecommerceTask.infrastructure.input.data.responses.orderResponse.OrderResponse;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.mapper.OrderMapper;
import com.semicolon.ecommerceTask.infrastructure.utilities.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderUseCase orderUseCase;
    private final OrderMapper orderDtoMapper;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<OrderResponse> createOrder(@AuthenticationPrincipal Jwt principal) {
        if (principal == null || principal.getClaimAsString("sub") == null) {throw new SecurityException(MessageUtil.AUTHENTICATED_USER_NOT_FOUND);}
        String userId = principal.getClaimAsString("sub");
        var order = orderUseCase.createOrder(userId);
        var response = orderDtoMapper.toResponse(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable UUID orderId,
                                                  @AuthenticationPrincipal Jwt principal) {
        if (principal == null || principal.getClaimAsString("sub") == null) {throw new SecurityException(MessageUtil.AUTHENTICATED_USER_NOT_FOUND);}
        var order = orderUseCase.getOrder(orderId);
        var response = orderDtoMapper.toResponse(order);
        return ResponseEntity.ok(response);
    }
}
