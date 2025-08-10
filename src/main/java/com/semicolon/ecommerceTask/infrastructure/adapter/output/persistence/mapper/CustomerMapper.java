package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper;

import com.semicolon.ecommerceTask.domain.model.CustomerDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.CustomerResponseDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "keycloakId", source = "keycloakId")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    CustomerDomainObject toDomainObject(CustomerEntity entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "keycloakId", source = "keycloakId")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    CustomerEntity toEntity(CustomerDomainObject domainObject);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    CustomerResponseDto toResponseDto(CustomerDomainObject domainObject);
}