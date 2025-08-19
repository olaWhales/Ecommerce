package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper;

import com.semicolon.ecommerceTask.domain.model.CartItemDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entities.CartItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mapping(target = "productEntity.id", source = "productId")
    @Mapping(target = "cartEntity.id", source = "cartId")
    CartItemEntity toDomain(CartItemDomainObject domain);

    @Mapping(target = "cartId" , source = "cartEntity.id")
    @Mapping(target = "productId", source = "productEntity.id")
    CartItemDomainObject toDomain(CartItemEntity entity);

}
