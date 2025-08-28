package com.semicolon.ecommerceTask.application.port.output.persistence;

import com.semicolon.ecommerceTask.domain.model.OrderDomainObject;
import com.semicolon.ecommerceTask.domain.model.OrderItemDomainObject;

import java.util.Optional;
import java.util.UUID;

public interface OrderItemPersistenceOutPort {
//    OrderDomainObject save(OrderDomainObject order);
    OrderItemDomainObject save(OrderItemDomainObject orderItem);
    Optional<OrderItemDomainObject> findById(UUID orderItemId);
    void deleteById(UUID orderItemId);
}

