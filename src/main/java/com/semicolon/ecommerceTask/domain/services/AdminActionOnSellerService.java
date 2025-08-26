package com.semicolon.ecommerceTask.domain.services;

import com.semicolon.ecommerceTask.application.port.input.AdminActionOnSellerUseCase;
import com.semicolon.ecommerceTask.application.port.output.KeycloakAdminOutPort;
import com.semicolon.ecommerceTask.application.port.output.NotificationOutPort;
import com.semicolon.ecommerceTask.application.port.output.PendingRegistrationOutPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.SellerFormSubmissionPersistenceOutPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.UserPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.exception.UserNotFoundException;
import com.semicolon.ecommerceTask.domain.model.ActionOnSellerApproveDomainObject;
import com.semicolon.ecommerceTask.domain.model.SellerFormSubmissionDomain;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.enumPackages.UserRole;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.mapper.AdminActionOnSellerFormMapper;
import com.semicolon.ecommerceTask.infrastructure.utilities.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
    private final NotificationOutPort notificationOutPort;

    @Transactional
    @Override
    public String approveSeller(UUID registrationId, ActionOnSellerApproveDomainObject request) {
        SellerFormSubmissionDomain submission =
            sellerFormSubmissionPersistenceOutPort.findById(registrationId).orElseThrow(() -> new UserNotFoundException(MessageUtil.SELLER_FORM_NOT_FOUND));

        String message;

        if (request.getIsApprove()) {
            keycloakAdminOutPort.assignRealmRoles(
                submission.getKeycloakUserId(),
                Collections.singletonList(UserRole.SELLER)
            );
            Optional<UserDomainObject> userOptional = userPersistenceOutPort.findById(submission.getKeycloakUserId());
            if(userOptional.isEmpty()){
                throw new UserNotFoundException(MessageUtil.USER_NOT_FOUND);
            }
            UserDomainObject user = userOptional.get();
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
//            return MessageUtil.HI + submission.getCustomerEmail() + MessageUtil.YOUR_FORM_TO_BECOME_A_SELLER_HAS_BEEN_SUCCESSFULLY_APPROVE;
//        } else {
//            pendingRegistrationOutPort.delete(registrationId);
//            return
            message = MessageUtil.HI + submission.getCustomerEmail() + MessageUtil.YOUR_FORM_TO_BECOME_A_SELLER_HAS_BEEN_SUCCESSFULLY_APPROVE;
        } else {
            pendingRegistrationOutPort.delete(registrationId);
            message = MessageUtil.HI + submission.getCustomerEmail() + MessageUtil.YOUR_FORM_TO_BECOME_A_SELLER_HAS_BEEN_REJECTED;
        }
        notificationOutPort.notifySeller(submission.getKeycloakUserId(), message);
        return message ;
    }

}