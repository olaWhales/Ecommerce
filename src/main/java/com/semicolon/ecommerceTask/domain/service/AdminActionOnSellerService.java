//package com.semicolon.ecommerceTask.domain.service;
//
//import com.semicolon.ecommerceTask.application.port.input.AdminActionOnSellerUseCase;
//import com.semicolon.ecommerceTask.application.port.output.persistence.SellerFormSubmissionPersistenceOutPort;
//import com.semicolon.ecommerceTask.application.port.output.KeycloakAdminOutPort;
//import com.semicolon.ecommerceTask.application.port.output.PendingRegistrationOutPort;
//import com.semicolon.ecommerceTask.application.port.output.persistence.UserPersistenceOutPort;
//import com.semicolon.ecommerceTask.domain.model.SellerFormSubmissionDomain;
//import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
//import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.ActionOnSellerApprovalRequest;
//import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.ActionOnSellerApprovalResponse;
//import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.enumPackage.UserRole;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class AdminActionOnSellerService implements AdminActionOnSellerUseCase {
//
//    private final PendingRegistrationOutPort pendingRegistrationOutPort;
//    private final KeycloakAdminOutPort keycloakAdminOutPort;
//    private final UserPersistenceOutPort userPersistenceOutPort;
//    private final SellerFormSubmissionPersistenceOutPort sellerFormSubmissionPersistenceOutPort;
//
//
//        @Override
//        public ActionOnSellerApprovalResponse approveSeller(UUID registrationId, ActionOnSellerApprovalRequest request) {
//            SellerFormSubmissionDomain submission =
//                    sellerFormSubmissionPersistenceOutPort.findById(registrationId)
//                            .orElseThrow(() -> new RuntimeException("Seller form not found"));
//
//            if (request.getIsApprove()) {
//                // Assign SELLER role in Keycloak
//                keycloakAdminOutPort.assignRealmRoles(
//                        submission.getKeycloakUserId(),
//                        Collections.singletonList(UserRole.SELLER.name())
//                );
//
//                // Optionally persist the user locally
//                userPersistenceOutPort.save(
//                    UserDomainObject.builder()
//                        .email(submission.getCustomerEmail())
//                        .roles(Collections.singletonList(UserRole.SELLER))
//                        .keycloakId(submission.getKeycloakUserId())
//                        .build()
//                );
//
//                // Remove from pending registrations
//                pendingRegistrationOutPort.delete(registrationId);
//
//                return ActionOnSellerApprovalResponse.builder()
//                        .approved(true)
//                        .message("Hi " + submission.getCustomerEmail()
//                                + ", your form to become a seller has been successfully approved.")
//                        .build();
//
//            } else {
//                // Remove from pending if rejected
//                pendingRegistrationOutPort.delete(registrationId);
//
//                return ActionOnSellerApprovalResponse.builder()
//                        .approved(false)
//                        .message("Hi " + submission.getCustomerEmail()
//                                + ", your form to become a seller has been rejected.")
//                        .build();
//            }
//        }
//}
package com.semicolon.ecommerceTask.domain.service;

import com.semicolon.ecommerceTask.application.port.input.AdminActionOnSellerUseCase;
import com.semicolon.ecommerceTask.application.port.output.persistence.SellerFormSubmissionPersistenceOutPort;
import com.semicolon.ecommerceTask.application.port.output.KeycloakAdminOutPort;
import com.semicolon.ecommerceTask.application.port.output.PendingRegistrationOutPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.UserPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.SellerFormSubmissionDomain;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.ActionOnSellerApprovalRequest;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.ActionOnSellerApprovalResponse;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.enumPackage.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminActionOnSellerService implements AdminActionOnSellerUseCase {

    private final PendingRegistrationOutPort pendingRegistrationOutPort;
    private final KeycloakAdminOutPort keycloakAdminOutPort;
    private final UserPersistenceOutPort userPersistenceOutPort;
    private final SellerFormSubmissionPersistenceOutPort sellerFormSubmissionPersistenceOutPort;

    @Override
    public ActionOnSellerApprovalResponse approveSeller(UUID registrationId, ActionOnSellerApprovalRequest request) {
        SellerFormSubmissionDomain submission =
                sellerFormSubmissionPersistenceOutPort.findById(registrationId)
                        .orElseThrow(() -> new RuntimeException("Seller form not found"));

        if (request.getIsApprove()) {
            // Assign SELLER role in Keycloak
            keycloakAdminOutPort.assignRealmRoles(
                    submission.getKeycloakUserId(),
                    Collections.singletonList(UserRole.SELLER.name())
            );

            // Optionally persist the user locally
            UserDomainObject savedUser = userPersistenceOutPort.save(
                    UserDomainObject.builder()
                            .email(submission.getCustomerEmail())
                            .roles(Collections.singletonList(UserRole.SELLER))
                            .keycloakId(submission.getKeycloakUserId())
                            .build()
            );

            // Remove from pending registrations
            pendingRegistrationOutPort.delete(registrationId);

            return ActionOnSellerApprovalResponse.builder()
//                    .userId(savedUser.getId()) // Added userId in the response
                    .approved(true)
                    .message("Hi " + submission.getCustomerEmail()
                            + ", your form to become a seller has been successfully approved.")
                    .build();

        } else {
            // Remove from pending if rejected
            pendingRegistrationOutPort.delete(registrationId);

            return ActionOnSellerApprovalResponse.builder()
//                    .userId(null) // No user ID if rejected
                    .approved(false)
                    .message("Hi " + submission.getCustomerEmail()
                            + ", your form to become a seller has been rejected.")
                    .build();
        }
    }
}
