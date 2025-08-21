package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper;

import com.semicolon.ecommerceTask.domain.model.CartDomainObject;
import com.semicolon.ecommerceTask.domain.model.CartItemDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.responses.CartItemResponse;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.responses.ProductToCartResponse;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entities.CartEntity;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entities.CartItemEntity;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entities.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "status", source = "domain.status")
    @Mapping(target = "id", source = "domain.id")
    CartEntity toEntity(CartDomainObject domain);

    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "id", source = "entity.id")
    CartDomainObject toDomain(CartEntity entity);

    @Mapping(target = "id", source = "entity.id")
//    @Mapping(target = "cartEntityId", source = "cartEntity")
    @Mapping(target = "cartEntityId", source = "cartEntity.id")
    @Mapping(target = "productId", source = "productEntity.id")
    @Mapping(target = "quantity", source = "quantity")
    CartItemDomainObject toDomain(CartItemEntity entity);

    @Mapping(target = "id", source = "domain.id")
    @Mapping(target = "cartEntityId", source = "domain.cartEntityId")
    @Mapping(target = "productId", source = "domain.productId")
    @Mapping(target = "quantity", source = "domain.quantity")
    CartItemResponse toResponseDto(CartItemDomainObject domain);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "price", source = "price")
    ProductToCartResponse toProductResponse(ProductEntity product);

    List<CartItemDomainObject> toDomainList(List<CartItemEntity> entities);
    List<CartItemResponse> toCartItemResponseList(List<CartItemEntity> cartItems);
}
