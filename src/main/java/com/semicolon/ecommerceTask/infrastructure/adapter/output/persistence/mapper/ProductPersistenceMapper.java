package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper;

import com.semicolon.ecommerceTask.domain.model.CategoryDomainObject;
import com.semicolon.ecommerceTask.domain.model.ManageProductDomainObject;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.CategoryRequestDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.manageProductDto.ProductUploadDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.manageProductDto.ProductUpdateDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.ProductResponseDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.CategoryEntity;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.ProductEntity;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ProductPersistenceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sellerId", ignore = true) // Set in service
    @Mapping(target = "categoryDomainObject", source = "categoryEntity", qualifiedByName = "toCategoryDomain")
    ManageProductDomainObject toDomain(ProductEntity entity);

//    @Mapping(target = "categoryEntity", source = "categoryDomainObject", qualifiedByName = "toCategoryEntityFromDomain")
//    ProductEntity toEntity(ManageProductDomainObject domain);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sellerId", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    @Mapping(target = "categoryEntity", source = "category", qualifiedByName = "toCategoryEntityFromDto")
    ProductEntity toEntity(ProductUploadDto dto);

    @Mapping(target = "imageUrl", source = "imageUrl") // Update imageUrl if provided
    ProductEntity toEntity(ProductUpdateDto dto, @MappingTarget ProductEntity entity);

    @Mapping(target = "category", source = "categoryDomainObject")
    ProductResponseDto toResponse(ManageProductDomainObject domain);

    @Mapping(target = "id", ignore = true) // Ignore ID as it's generated
    @Mapping(target = "roles", ignore = true) // Ignore roles for now; handle separately if needed
    UserEntity toUserEntity(UserDomainObject domainObject);

    
    @Mapping(target = "email", source = "email")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    UserDomainObject toDomainObject(UserEntity entity);

    @Named("toCategoryDomain")
    default CategoryDomainObject toCategoryDomain(CategoryEntity entity) {
        return CategoryDomainObject.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    @Named("toCategoryEntityFromDomain")
    default CategoryEntity toCategoryEntityFromDomain(CategoryDomainObject category) {
        CategoryEntity entity = new CategoryEntity();
        entity.setId(category.getId());
        entity.setName(category.getName());
        return entity;
    }

    @Named("toCategoryEntityFromDto")
    default CategoryEntity toCategoryEntityFromDto(CategoryRequestDto categoryDto) {
        CategoryEntity entity = new CategoryEntity();
        entity.setId(UUID.randomUUID());
        entity.setName(categoryDto.getName());
        return entity;
    }
}