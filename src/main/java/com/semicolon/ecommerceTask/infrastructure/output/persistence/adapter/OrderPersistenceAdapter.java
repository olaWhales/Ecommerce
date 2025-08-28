package com.semicolon.ecommerceTask.infrastructure.output.persistence.adapter;

import com.semicolon.ecommerceTask.application.port.output.persistence.OrderPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.OrderDomainObject;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.persistenceEntities.OrderEntity;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.mapper.OrderMapper;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderPersistenceAdapter implements OrderPersistenceOutPort {

    private final OrderRepository orderJpaRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderDomainObject save(OrderDomainObject order) {
        OrderEntity entity = orderMapper.toEntity(order);
        OrderEntity saved = orderJpaRepository.save(entity);
        return orderMapper.toDomain(saved);
    }

    @Override
    public Optional<OrderDomainObject> findById(UUID orderId) {
        return orderJpaRepository.findById(orderId)
                .map(orderMapper::toDomain);
    }
}
