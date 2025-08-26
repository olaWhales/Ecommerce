package com.semicolon.ecommerceTask.infrastructure.output.persistence.mapper.productManagentsMapper;

import com.semicolon.ecommerceTask.domain.model.ManageProductDomainObject;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.persistenceEntities.ProductEntity;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.mapper.CategoryMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductPersistenceMapper {

    @Mapping(target = "categoryDomainObject", source = "category")
    ManageProductDomainObject   toProductDomain(ProductEntity entity);

    @Mapping(target = "category", source = "categoryDomainObject")
    @Mapping(target = "id", ignore = true)
    ProductEntity toProductEntity(ManageProductDomainObject domain);

}

