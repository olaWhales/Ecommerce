package com.semicolon.ecommerceTask.infrastructure.adapter.configuration;

import com.semicolon.ecommerceTask.application.port.output.KeycloakSuperAdminClient;
import com.semicolon.ecommerceTask.application.port.output.UserRepository;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserRole;
import com.semicolon.ecommerceTask.domain.model.UsersModel;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.superAdmin.KeycloakUserDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder {

    private final UserRepository userRepository;
    private final KeycloakSuperAdminClient keycloakAdminClient;

    @PostConstruct
    public void seedSuperAdmin() {
        if (userRepository.findByEmail("superadmin@ecommerce.com").isEmpty()) {
            UsersModel superAdmin = new UsersModel(
                "Super Admin",
                "superadmin@ecommerce.com",
                "superSecurePassword123",
                UserRole.SUPERADMIN
            );
            userRepository.save(superAdmin);

            KeycloakUserDto dto = new KeycloakUserDto(
                    superAdmin.getName(),
                    superAdmin.getEmail(),
                    superAdmin.getPassword(),
                    superAdmin.getRole().name()
            );
            keycloakAdminClient.createUserWithRole(dto);
        }
    }
}
