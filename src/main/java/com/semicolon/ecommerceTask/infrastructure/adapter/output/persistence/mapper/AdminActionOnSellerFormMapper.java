package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper;

import com.semicolon.ecommerceTask.domain.model.ActionOnSellerApproveDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.requests.ActionOnSellerApprovalRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminActionOnSellerFormMapper {

    ActionOnSellerApproveDomainObject toDomain(ActionOnSellerApprovalRequest request);
    ActionOnSellerApprovalRequest toDto(ActionOnSellerApproveDomainObject domainObject);
}
