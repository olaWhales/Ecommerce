package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper;

import com.semicolon.ecommerceTask.domain.model.CartItemDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.responses.CartItemResponse;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entities.CartItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "cartEntityId", source = "entity.cartEntity.id")
    @Mapping(target = "productId", source = "entity.productEntity.id")
    @Mapping(target = "quantity", source = "entity.quantity")
    CartItemDomainObject toDomain(CartItemEntity entity);

    @Mapping(target = "id", source = "domain.id")
    @Mapping(target = "cartEntity.id", source = "cartEntityId")
    @Mapping(target = "productEntity.id", source = "productId")
    @Mapping(target = "quantity", source = "quantity")
    CartItemEntity toEntity(CartItemDomainObject domain);

    @Mapping(target = "id", source = "domain.id")
    @Mapping(target = "cartEntityId", source = "domain.cartEntityId")
    @Mapping(target = "productId", source = "domain.productId")
    @Mapping(target = "quantity", source = "domain.quantity")
    CartItemResponse toResponseDto(CartItemDomainObject domain);

    List<CartItemDomainObject> toDomainList(List<CartItemEntity> entities);
    List<CartItemResponse> toResponseDtoList(List<CartItemDomainObject> domains);
}