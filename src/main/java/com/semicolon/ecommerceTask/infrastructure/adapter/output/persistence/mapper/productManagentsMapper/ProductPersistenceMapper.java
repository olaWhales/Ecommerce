package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.productManagentsMapper;

import com.semicolon.ecommerceTask.domain.model.ManageProductDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entities.ProductEntity;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.CategoryMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductPersistenceMapper {

    @Mapping(target = "categoryDomainObject", source = "categoryEntity")
    ManageProductDomainObject toProductDomain(ProductEntity entity);

    @Mapping(target = "categoryEntity", source = "categoryDomainObject")
    @Mapping(target = "id", ignore = true)
    ProductEntity toProductEntity(ManageProductDomainObject domain);

}
