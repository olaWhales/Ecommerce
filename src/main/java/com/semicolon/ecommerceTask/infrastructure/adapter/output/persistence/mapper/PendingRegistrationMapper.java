package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper;

import com.semicolon.ecommerceTask.domain.model.PendingRegistrationDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.userEntity.PendingAdminRegistrationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PendingRegistrationMapper {

    // Map a Domain Object to an Entity
//    PendingAdminRegistrationEntity toEntity(PendingRegistrationDomainObject domainObject);

    // Map an Entity back to a Domain Object
//    @Mapping(target = "firstName" , source = "firstName")
//    @Mapping(target = "lastName" , source = "lastName")
//    @Mapping(target = "email" , source = "email")
    @Mapping(target = "id" , source = "id")
    PendingRegistrationDomainObject toDomain(PendingRegistrationDomainObject entity);

    PendingRegistrationDomainObject toDomain(PendingAdminRegistrationEntity entity);

    PendingAdminRegistrationEntity toEntity(PendingRegistrationDomainObject domainObject);

}