package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper;

import com.semicolon.ecommerceTask.domain.model.PendingSellerRegistration;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.PendingSellerRegistrationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PendingSellerRegistrationMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "customerEmail", source = "customerEmail")
    @Mapping(target = "businessName", source = "businessName")
    @Mapping(target = "details", source = "details")
    @Mapping(target = "submissionDate", source = "submissionDate")
    PendingSellerRegistration toDomainObject(PendingSellerRegistrationEntity entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "customerEmail", source = "customerEmail")
    @Mapping(target = "businessName", source = "businessName")
    @Mapping(target = "details", source = "details")
    @Mapping(target = "submissionDate", source = "submissionDate")
    PendingSellerRegistrationEntity toEntity(PendingSellerRegistration domainObject);

    List<PendingSellerRegistration> toDomainObjectList(List<PendingSellerRegistrationEntity> entities);
}