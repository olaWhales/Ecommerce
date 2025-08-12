package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper;

import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.AdminEntity;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.PendingAdminRegistrationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PendingRegistrationMapper {

    // Map a Domain Object to an Entity
    PendingAdminRegistrationEntity toEntity(AdminEntity.PendingRegistrationDomainObject domainObject);

    // Map an Entity back to a Domain Object
    AdminEntity.PendingRegistrationDomainObject toDomain(PendingAdminRegistrationEntity entity);
}