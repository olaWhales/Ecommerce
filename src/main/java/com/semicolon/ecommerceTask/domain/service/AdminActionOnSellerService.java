package com.semicolon.ecommerceTask.domain.service;

import com.semicolon.ecommerceTask.application.port.input.AdminActionOnSellerUseCase;
import com.semicolon.ecommerceTask.application.port.output.KeycloakAdminOutPort;
import com.semicolon.ecommerceTask.application.port.output.PendingRegistrationOutPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.SellerFormSubmissionPersistenceOutPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.UserPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.exception.UserNotFoundException;
import com.semicolon.ecommerceTask.domain.model.ActionOnSellerApproveDomainObject;
import com.semicolon.ecommerceTask.domain.model.SellerFormSubmissionDomain;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.enumPackage.UserRole;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.AdminActionOnSellerFormMapper;
import com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminActionOnSellerService implements AdminActionOnSellerUseCase {

    private final PendingRegistrationOutPort pendingRegistrationOutPort;
    private final KeycloakAdminOutPort keycloakAdminOutPort;
    private final UserPersistenceOutPort userPersistenceOutPort;
    private final SellerFormSubmissionPersistenceOutPort sellerFormSubmissionPersistenceOutPort;
    private final AdminActionOnSellerFormMapper mapper;

    @Transactional
    @Override
    public String approveSeller(UUID registrationId, ActionOnSellerApproveDomainObject request) {
        SellerFormSubmissionDomain submission =
            sellerFormSubmissionPersistenceOutPort.findById(registrationId).orElseThrow(() -> new UserNotFoundException(MessageUtil.SELLER_FORM_NOT_FOUND));
        if (request.getIsApprove()) {
            keycloakAdminOutPort.assignRealmRoles(
                submission.getKeycloakUserId(),
                Collections.singletonList(UserRole.SELLER)
            );
            UserDomainObject user = userPersistenceOutPort.findById(submission.getKeycloakUserId());
            List<UserRole> currentRoles = user.getRoles();
            if (!currentRoles.contains(UserRole.SELLER)) {
                currentRoles.add(UserRole.SELLER);
                userPersistenceOutPort.save(
                    UserDomainObject.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .password(user.getPassword())
                        .roles(currentRoles)
                        .build()
                );
            }
            pendingRegistrationOutPort.delete(registrationId);
            return MessageUtil.HI + submission.getCustomerEmail() + MessageUtil.YOUR_FORM_TO_BECOME_A_SELLER_HAS_BEEN_SUCCESSFULLY_APPROVE;
        } else {
            pendingRegistrationOutPort.delete(registrationId);
            return MessageUtil.HI + submission.getCustomerEmail() + MessageUtil.YOUR_FORM_TO_BECOME_A_SELLER_HAS_BEEN_REJECTED;
        }
    }

}