package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper;

import com.semicolon.ecommerceTask.domain.model.SellerFormSubmissionDomain;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.SellerFormSubmissionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SellerFormSubmissionMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "customerEmail", source = "customerEmail")
    @Mapping(target = "businessName", source = "businessName")
    @Mapping(target = "details", source = "details")
    @Mapping(target = "submissionDate", source = "submissionDate")
    @Mapping(target = "keycloakUserId", source = "keycloakUserId")
    SellerFormSubmissionDomain toDomainObject(SellerFormSubmissionEntity entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "customerEmail", source = "customerEmail")
    @Mapping(target = "businessName", source = "businessName")
    @Mapping(target = "details", source = "details")
    @Mapping(target = "submissionDate", source = "submissionDate")
    @Mapping(target = "keycloakUserId", source = "keycloakUserId")
    SellerFormSubmissionEntity toEntity(SellerFormSubmissionDomain domainObject);

    List<SellerFormSubmissionDomain> toDomainObjectList(List<SellerFormSubmissionEntity> entities);
}