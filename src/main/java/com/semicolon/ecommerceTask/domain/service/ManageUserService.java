//package com.semicolon.ecommerceTask.domain.service;
//
//import com.semicolon.ecommerceTask.application.port.input.ManageUserUsecase;
//import com.semicolon.ecommerceTask.application.port.output.persistence.UserPersistenceOutPort;
//import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
//import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.UpdateUserRequest;
//import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.UserResponse;
//import com.semicolon.ecommerceTask.infrastructure.adapter.output.keycloack.KeycloakUserAdapter;
//import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserRole;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class ManageUserService implements ManageUserUsecase {
//
////    private static final Logger log = LoggerFactory.getLogger(ManageUserService.class);
//    private final KeycloakUserAdapter keycloakUserAdapter;
//    private final UserPersistenceOutPort userPersistenceOutPort;
//    private final JavaMailSender mailSender;
//    private final Map<String, String> verificationCodes = new HashMap<>(); // In-memory store for demo
//
//    @Override
//    @Transactional
//    public UserResponse createUser(UserDomainObject user) {
//        if (user.getEmail() == null || user.getPassword() == null) {
//            throw new IllegalArgumentException("Email and password are required");
//        }
//
//        // Step 1: Generate and send verification code
//        String verificationCode = generateVerificationCode();
//        verificationCodes.put(user.getEmail(), verificationCode);
//        sendVerificationEmail(user.getEmail(), verificationCode);
//
//        // Step 2: Create user in Keycloak with unverified email
//        user.setRoles(Collections.singletonList(UserRole.ADMIN));
//        String keycloakId = keycloakUserAdapter.createUser(user);
//        user.setKeycloakId(keycloakId);
//
//        // Step 3: Wait for verification (simplified logic)
//        // In a real app, this would involve a separate endpoint to verify the code
//        log.info("User created in Keycloak with ID: {}. Awaiting verification.", keycloakId);
//        return UserResponse.builder()
//                .email(user.getEmail())
//                .name(user.getFirstName() + " " + user.getLastName())
//                .keycloakId(user.getKeycloakId())
//                .build();
//    }
//
//    @Override
//    public void deleteUser(String userId) {
//
//    }
//
//    @Override
//    public UserResponse updateUser(String userId, UpdateUserRequest updateUserRequest) {
//        return null;
//    }
//
//    public void verifyAndCompleteUser(String email, String code, String name, String password) {
//        String storedCode = verificationCodes.get(email);
//        if (storedCode == null || !storedCode.equals(code)) {
//            throw new IllegalArgumentException("Invalid or expired verification code");
//        }
//
//        UserDomainObject user = UserDomainObject.builder()
//                .firstName(name)
//                .lastName(name)
//                .email(email)
//                .password(password)
//                .roles(Collections.singletonList(UserRole.ADMIN))
//                .keycloakId(verificationCodes.remove(email)) // Remove after verification
//                .build();
//
//        // Update Keycloak user (mark email as verified)
//        // Note: Keycloak requires admin access to verify email; simplify by trusting input for now
//        userPersistenceOutPort.saveLocalUser(user.getKeycloakId(), user);
//        log.info("User verified and saved in local DB with email: {}", email);
//    }
//
//    private String generateVerificationCode() {
//        Random random = new Random();
//        return String.format("%06d", random.nextInt(1000000));
//    }
//
//    private void sendVerificationEmail(String to, String code) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject("Email Verification Code");
//        message.setText("Your verification code is: " + code);
//        mailSender.send(message);
//        log.info("Verification code sent to: {}", to);
//    }
//}