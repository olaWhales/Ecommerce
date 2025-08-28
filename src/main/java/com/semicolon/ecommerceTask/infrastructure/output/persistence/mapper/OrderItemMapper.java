package com.semicolon.ecommerceTask.infrastructure.output.persistence.mapper;

import com.semicolon.ecommerceTask.domain.model.OrderItemDomainObject;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.persistenceEntities.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(source = "orderEntity.id" , target = "orderId")
    @Mapping(source = "productEntity.id",target = "productId")
    OrderItemDomainObject toDomain(OrderItemEntity entity);

    @Mapping(source = "orderId",target = "orderEntity.id")
    @Mapping(source = "productId",target = "productEntity.id")
    OrderItemEntity toEntity(OrderItemDomainObject domain);
}
