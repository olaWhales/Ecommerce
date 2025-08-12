package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper;

import com.semicolon.ecommerceTask.domain.model.ProductDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.ProductUpdateDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.ProductUploadDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductPersistenceMapper {

    ProductDomainObject toDomain(ProductUploadDto productDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sellerId", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    void updateDomainFromDto(ProductUpdateDto productDto, @MappingTarget ProductDomainObject domainObject);

    ProductEntity toEntity(ProductDomainObject domainObject);
    ProductDomainObject toDomain(ProductEntity entity);
    List<ProductDomainObject> toDomainList(List<ProductEntity> entities);
}