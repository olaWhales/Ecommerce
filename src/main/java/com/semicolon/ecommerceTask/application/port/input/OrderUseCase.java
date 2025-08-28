package com.semicolon.ecommerceTask.application.port.input;

import com.semicolon.ecommerceTask.domain.model.OrderDomainObject;

import java.util.UUID;

public interface OrderUseCase {

    OrderDomainObject createOrder(String userId);
    OrderDomainObject getOrder(UUID orderId);
}
