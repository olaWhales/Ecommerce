package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper;

import com.semicolon.ecommerceTask.domain.model.PendingRegistrationDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.userEntity.PendingAdminRegistrationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PendingRegistrationMapper {

    // Map a Domain Object to an Entity
    PendingAdminRegistrationEntity toEntity(PendingRegistrationDomainObject domainObject);

    // Map an Entity back to a Domain Object
    PendingRegistrationDomainObject toDomain(PendingAdminRegistrationEntity entity);
}