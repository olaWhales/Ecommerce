package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper;

import com.semicolon.ecommerceTask.domain.model.ProductDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.ProductEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductPersistenceMapper {
    ProductEntity toEntity(ProductDomainObject domainObject);
    ProductDomainObject toDomain(ProductEntity entity);
    List<ProductDomainObject> toDomainList(List<ProductEntity> entities);
}