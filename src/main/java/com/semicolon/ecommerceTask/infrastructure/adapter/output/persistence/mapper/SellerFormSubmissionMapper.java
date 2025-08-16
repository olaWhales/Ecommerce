package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper;

import com.semicolon.ecommerceTask.domain.model.SellerFormSubmissionDomain;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.CustomerRegistrationDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.SellerRegistrationFormDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.userEntity.SellerFormSubmissionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

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
//    @Mapping(target = "customerEmail" , source = "customerEmail")
    @Mapping(target = "details" , source = "details")
    @Mapping(target = "businessName", source = "businessName")
    @Mapping(target = "submissionDate" , ignore = true)
    @Mapping(target = "keycloakUserId" , ignore = true)
    @Mapping(target = "version" , ignore = true)
    SellerFormSubmissionDomain toDomainFromDto(SellerRegistrationFormDto dto);


    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "customerEmail", source = "customerEmail")
    @Mapping(target = "keycloakUserId", source = "keycloakUserId")
    @Mapping(target = "submissionDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "version" , constant = "0L")
    SellerFormSubmissionDomain enrichDomainFromTemplate(
            SellerFormSubmissionDomain template,
//            UUID registrationId,
            String customerEmail,
            String keycloakUserId
            );


//    @Mapping(target = "customerEmail" , source = "customerEmail")
//    @Mapping(target = "businessName" , source = "businessName")
//    @Mapping(target = "" , source = "")
//    UserDomainObject toUserDomainObject (SellerFormSubmissionEntity sellerFormSubmissionEntity);
//
//    @Mapping(target = "customerEmail" , source = "customerEmail")
//    @Mapping(target = "businessName" , source = "businessEmail")
//    SellerFormSubmissionEntity toSellerSubmissionFormEntity(CustomerRegistrationDto userDomainObject);


    List<SellerFormSubmissionDomain> toDomainObjectList(List<SellerFormSubmissionEntity> entities);
}