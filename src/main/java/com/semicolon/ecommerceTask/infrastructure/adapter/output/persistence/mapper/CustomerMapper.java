package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper;

import com.semicolon.ecommerceTask.domain.model.CustomerDomainObject;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.UserDomainObjectResponse;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    CustomerDomainObject toDomainObject(UserEntity entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    UserEntity  toEntity(CustomerDomainObject domainObject);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    UserDomainObjectResponse toResponseDto(UserDomainObject domainObject);

}