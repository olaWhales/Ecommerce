package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper;

import com.semicolon.ecommerceTask.domain.model.SellerFormSubmissionDomain;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.SellerRegistrationFormRequest;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.sellerRegistrationResponse.SellerFormSubmissionResponse;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.temporaryPendingRegistrationEntity.SellerFormSubmissionEntity;
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
    @Mapping(target = "version", source = "version")
    SellerFormSubmissionDomain toDomainObject(SellerFormSubmissionEntity entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "customerEmail", source = "customerEmail")
    @Mapping(target = "businessName", source = "businessName")
    @Mapping(target = "details", source = "details")
    @Mapping(target = "submissionDate", source = "submissionDate")
    @Mapping(target = "keycloakUserId", source = "keycloakUserId")
    @Mapping(target = "version", source = "version")
    SellerFormSubmissionEntity toEntity(SellerFormSubmissionDomain domainObject);

    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "details" , source = "details")
    @Mapping(target = "businessName", source = "businessName")
    @Mapping(target = "submissionDate" , ignore = true)
    @Mapping(target = "keycloakUserId" , ignore = true)
    @Mapping(target = "version" , ignore = true)
    SellerFormSubmissionDomain toDomainFromDto(SellerRegistrationFormRequest dto);

    @Mapping(target = "message", constant = "Your registration has been submitted successfully. You will be notified once your application is approved.")
    @Mapping(target = "registrationId", source = "id")
    @Mapping(target = "keycloakUserId", source = "keycloakUserId")
    SellerFormSubmissionResponse toResponse(SellerFormSubmissionDomain domain);

    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "customerEmail", source = "customerEmail")
    @Mapping(target = "keycloakUserId", source = "keycloakUserId")
    @Mapping(target = "submissionDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "version" , constant = "0L")
    SellerFormSubmissionDomain enrichDomainFromTemplate(
            SellerFormSubmissionDomain template,
            String customerEmail,
            String keycloakUserId
            );

    List<SellerFormSubmissionDomain> toDomainObjectList(List<SellerFormSubmissionEntity> entities);
}