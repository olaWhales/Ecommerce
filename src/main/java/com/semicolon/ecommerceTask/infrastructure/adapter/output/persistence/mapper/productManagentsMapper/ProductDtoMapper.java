package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.productManagentsMapper;

import com.semicolon.ecommerceTask.domain.model.CategoryDomainObject;
import com.semicolon.ecommerceTask.domain.model.ManageProductDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.requests.manageProductDto.ProductUpdateDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.requests.manageProductDto.ProductUploadDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.responses.ProductRegResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductDtoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sellerId", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    @Mapping(target = "categoryDomainObject", source = "categoryDomainObject")
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "price", source = "dto.price")
    @Mapping(target = "inStockQuantity", source = "dto.inStockQuantity")
    ManageProductDomainObject toDomain(ProductUploadDto dto, CategoryDomainObject categoryDomainObject);

    // Update → Domain
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sellerId", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    @Mapping(target = "categoryDomainObject", source = "categoryDomainObject")
    // explicitly from dto
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "price", source = "dto.price")
    @Mapping(target = "inStockQuantity", source = "dto.inStockQuantity")
    void updateDomainFromDto(ProductUpdateDto dto, @MappingTarget ManageProductDomainObject domain, CategoryDomainObject categoryDomainObject);

    ProductRegResponse toResponse(ManageProductDomainObject domain);
}
