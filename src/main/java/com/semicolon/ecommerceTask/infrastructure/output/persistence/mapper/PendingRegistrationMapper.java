package com.semicolon.ecommerceTask.infrastructure.output.persistence.mapper;

import com.semicolon.ecommerceTask.domain.model.PendingRegistrationDomainObject;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.temporaryPendingRegistrationEntity.PendingAdminRegistrationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PendingRegistrationMapper {

    @Mapping(target = "id" , source = "id")
    PendingRegistrationDomainObject toDomain(PendingRegistrationDomainObject entity);

    PendingRegistrationDomainObject toDomain(PendingAdminRegistrationEntity entity);

    PendingAdminRegistrationEntity toEntity(PendingRegistrationDomainObject domainObject);

}