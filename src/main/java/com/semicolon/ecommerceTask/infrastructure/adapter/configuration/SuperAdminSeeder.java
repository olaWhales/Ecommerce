package com.semicolon.ecommerceTask.infrastructure.adapter.configuration;

import com.semicolon.ecommerceTask.application.port.output.persistence.UserPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserRole;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SuperAdminSeeder {

    private final UserPersistenceOutPort userPersistenceOutPort;

    @PostConstruct
    public void seedSuperAdmin() {
        // Check if SuperAdmin already exists
        if (userPersistenceOutPort.existsByEmail("superadmin@example.com")) {
            return;
        }

        // Create SuperAdmin user
        UserDomainObject superAdmin = new UserDomainObject();
        superAdmin.setName("Super Admin");
        superAdmin.setEmail("superadmin@example.com");
        superAdmin.setPassword("1111");
        superAdmin.setRoles(List.of(UserRole.SUPERADMIN));
//        UserDomainObject superAdmin = UserDomainObject.builder()
//                .name("Super Admin")
//                .email()
//                .password("supersecurepassword") // should be hashed if not handled automatically
//                .roles(List.of(UserRole.SUPERADMIN)
//                .build());

        userPersistenceOutPort.saveUser(superAdmin);
        System.out.println("✅ SuperAdmin seeded.");
    }
}
