package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper;

import com.semicolon.ecommerceTask.domain.model.ActionOnSellerApproveDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.ActionOnSellerApprovalRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminActionOnSellerFormMapper {

    // DTO → Domain
    ActionOnSellerApproveDomainObject toDomain(ActionOnSellerApprovalRequest request);

    // Domain → DTO
    ActionOnSellerApprovalRequest toDto(ActionOnSellerApproveDomainObject domainObject);
}
