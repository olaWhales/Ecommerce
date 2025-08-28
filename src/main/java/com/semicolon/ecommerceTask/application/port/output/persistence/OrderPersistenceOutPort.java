package com.semicolon.ecommerceTask.application.port.output.persistence;

import com.semicolon.ecommerceTask.domain.model.OrderDomainObject;

import java.util.Optional;
import java.util.UUID;

public interface OrderPersistenceOutPort {
    OrderDomainObject save(OrderDomainObject order);
    Optional<OrderDomainObject> findById(UUID orderId);
}

