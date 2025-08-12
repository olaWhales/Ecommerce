package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence;

import com.semicolon.ecommerceTask.application.port.output.persistence.AdminPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.AdminDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.AdminEntity;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.PendingAdminRegistrationEntity;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.AdminMapper;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.PendingRegistrationMapper;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.repository.AdminRepository;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.repository.PendingRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AdminPersistenceAdapter implements AdminPersistenceOutPort {

    private final AdminRepository adminRepository;
    private final PendingRegistrationRepository pendingRegistrationRepository;
    private final AdminMapper adminMapper;
    private final PendingRegistrationMapper pendingRegistrationMapper;

    @Override
    public boolean existsByEmail(String email) {
        return adminRepository.existsByEmail(email);
    }

    @Override
    public AdminDomainObject saveAdmin(AdminDomainObject admin) {
        AdminEntity entity = adminMapper.toEntity(admin);
        return adminMapper.toDomainObject(adminRepository.save(entity));
    }

    @Override
    public void deleteAdmin(String email) {
        adminRepository.deleteByEmail(email);
    }

    @Override
    public Optional<AdminDomainObject> findByEmail(String email) {
        return adminRepository.findByEmail(email).map(adminMapper::toDomainObject);
    }

    @Override
    public List<AdminDomainObject> findAllAdmins() {
        return adminMapper.toDomainObjectList(adminRepository.findAll());
    }

    @Override
    public void createPendingRegistration(String email, String token, LocalDateTime expiration) {
        PendingAdminRegistrationEntity entity = PendingAdminRegistrationEntity.builder()
                .email(email)
                .token(token)
                .expiration(expiration)
                .build();
        pendingRegistrationRepository.save(entity);
    }

    @Override
    public Optional<AdminEntity.PendingRegistrationDomainObject> findPendingRegistrationByEmail(String email) {
        return pendingRegistrationRepository.findByEmail(email)
                .map(pendingRegistrationMapper::toDomain);
    }

    @Override
    public void updatePendingRegistration(String email, String token, LocalDateTime expiration) {
        PendingAdminRegistrationEntity entity = pendingRegistrationRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Pending registration not found for update"));

        entity.setToken(token);
        entity.setExpiration(expiration);

        pendingRegistrationRepository.save(entity);
    }

    @Override
    public void deletePendingRegistration(String email) {
        pendingRegistrationRepository.deleteByEmail(email); // <-- Changed deleteById to deleteByEmail
    }
}