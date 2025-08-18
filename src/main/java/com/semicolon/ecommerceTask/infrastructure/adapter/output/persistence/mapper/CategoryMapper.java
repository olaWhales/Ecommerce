package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper;

import com.semicolon.ecommerceTask.domain.model.CategoryDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.requests.CategoryCreationDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.responses.CategoryRegResponse;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entities.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "name" , source = "name")
    CategoryDomainObject toCategoryDomain(CategoryCreationDto dto);

    @Mapping(target = "id" , source = "id")
    @Mapping(target = "name" , source = "name")
    CategoryDomainObject toCategoryDomain(CategoryEntity entity);

    @Mapping(target = "name" , source = "name")
    CategoryEntity toCategoryEntity(CategoryDomainObject domain);

    CategoryRegResponse toCategoryResponseDto(UUID domain);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CategoryRegResponse toCategoryResponseDto(CategoryDomainObject domain);
}
