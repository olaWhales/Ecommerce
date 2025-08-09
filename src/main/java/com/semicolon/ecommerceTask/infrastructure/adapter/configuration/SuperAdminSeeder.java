//package com.semicolon.ecommerceTask.infrastructure.adapter.configuration;
//
//import com.semicolon.ecommerceTask.application.port.input.CreateSuperAdminUseCase;
//import com.semicolon.ecommerceTask.application.port.output.persistence.UserPersistenceOutPort;
//import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
//import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserRole;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class SuperAdminSeeder implements CommandLineRunner {
//
//    private final CreateSuperAdminUseCase createSuperAdminUseCase;
//
//
//    @Override
//    public void run(String... args) throws Exception {
//        // Check if SuperAdmin exists in DB before creating
//        // (Optional: check by email in persistence port)
//        createSuperAdminUseCase.createSuperAdmin();
//    }
//}
