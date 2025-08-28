package com.semicolon.ecommerceTask.infrastructure.output.persistence.adapter;

import com.semicolon.ecommerceTask.application.port.output.persistence.OrderItemPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.OrderItemDomainObject;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.persistenceEntities.OrderItemEntity;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.mapper.OrderItemMapper;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.repositories.OrderItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderItemPersistenceAdapter implements OrderItemPersistenceOutPort {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderItemDomainObject save(OrderItemDomainObject orderItem) {
        OrderItemEntity entity = orderItemMapper.toEntity(orderItem);
        OrderItemEntity saved = orderItemRepository.save(entity);
        return orderItemMapper.toDomain(saved);
    }

    @Override
    public Optional<OrderItemDomainObject> findById(UUID orderItemId) {
        return orderItemRepository.findById(orderItemId)
                .map(orderItemMapper::toDomain);
    }

    @Override
    public void deleteById(UUID orderItemId) {
        orderItemRepository.deleteById(orderItemId);
    }
}
