package com.semicolon.ecommerceTask.domain.services;

import com.semicolon.ecommerceTask.application.port.input.OrderUseCase;
import com.semicolon.ecommerceTask.application.port.output.persistence.*;
import com.semicolon.ecommerceTask.domain.model.*;
import com.semicolon.ecommerceTask.application.port.output.persistence.OrderItemPersistenceOutPort;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.enumPackages.CartStatus;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.enumPackages.OrderStatus;
import com.semicolon.ecommerceTask.infrastructure.utilities.MessageUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService implements OrderUseCase {

    private final OrderPersistenceOutPort orderPersistenceOutPort;
    private final OrderItemPersistenceOutPort orderItemPersistenceOutPort;
    private final CartPersistenceOutPort cartPersistenceOutPort;
    private final CartItemPersistenceOutPort cartItemPersistenceOutPort;
    private final UserPersistenceOutPort userPersistenceOutPort;

    @Override
    public OrderDomainObject createOrder(String userId) {
        CartDomainObject cart = cartPersistenceOutPort.findByUserIdAndStatus(userId, CartStatus.ACTIVE).orElseThrow(() -> new IllegalArgumentException(MessageUtil.NO_ACTIVE_CART_FOUND));
        List<CartItemDomainObject> cartItems = cartItemPersistenceOutPort.findByCartId(cart.getId());
        if (cartItems.isEmpty()) {throw new IllegalArgumentException(MessageUtil.CART_EMPTY);}
        UserDomainObject user = userPersistenceOutPort.findById(userId).orElseThrow(() -> new IllegalArgumentException(MessageUtil.USER_NOT_FOUND));
        BigDecimal totalAmount = cartItems.stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        OrderDomainObject order = OrderDomainObject.builder()
                .user(UUID.fromString(user.getId()))
                .status(OrderStatus.PENDING)
                .totalAmount(totalAmount)
                .dateCreated(LocalDateTime.now())
                .build();
        OrderDomainObject savedOrder = orderPersistenceOutPort.save(order);
        List<OrderItemDomainObject> orderItems = cartItems.stream().map(ci ->
            OrderItemDomainObject.builder()
                .orderId(savedOrder.getId())
                .productId(ci.getProductId())
                .unitPrice(ci.getUnitPrice())
                .quantity(ci.getQuantity())
                .build())
                .map(orderItemPersistenceOutPort::save).toList();
        savedOrder.setItems(orderItems);
        cartPersistenceOutPort.deleteById(cart.getId());
        return savedOrder;
    }

    @Override
    public OrderDomainObject getOrder(UUID orderId) {
        return orderPersistenceOutPort.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException(MessageUtil.ORDER_NOT_FOUND));
    }
}
