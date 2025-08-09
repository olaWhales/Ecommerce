package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence;

import com.semicolon.ecommerceTask.application.port.output.persistence.AdminPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.AdminDomainObject;
import com.semicolon.ecommerceTask.domain.model.PendingRegistration;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.AdminEntity;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.PendingRegistrationEntity;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.AdminEntityMapper;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.AdminMapper;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.repository.AdminRepository;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.repository.PendingRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AdminPersistenceAdapter implements AdminPersistenceOutPort {

    private final AdminRepository adminRepository;
    private final PendingRegistrationRepository pendingRegistrationRepository;

    @Override
    public boolean existsByEmail(String email) {
        return adminRepository.existsByEmail(email);
    }

    @Override
    public void savePendingRegistration(String email, String token, LocalDateTime expiration) {
        PendingRegistrationEntity entity = AdminMapper.INSTANCE.toPendingEntity(email, token, expiration);
        pendingRegistrationRepository.save(entity);
    }

    @Override
    public Optional<PendingRegistration> findPendingTokenByEmail(String email) {
        return pendingRegistrationRepository.findByEmail(email)
                .map(entity -> new PendingRegistration(entity.getEmail(), entity.getToken(), entity.getExpiration()));
    }

    @Override
    public void saveAdmin(AdminDomainObject admin) {
        AdminEntity entity = AdminEntityMapper.toEntity(admin);
        adminRepository.save(entity);
    }

    @Override
    public void deletePendingRegistration(String email) {
        pendingRegistrationRepository.deleteByEmail(email);
    }

    @Override
    public Optional<AdminDomainObject> findByEmail(String email) {
        return adminRepository.findByEmail(email)
                .map(AdminEntityMapper::toDomainObject);
    }

    @Override
    public void deleteAdmin(String email) {
        adminRepository.deleteByEmail(email);
    }

    @Override
    public List<AdminDomainObject> findAllAdmins() {
        return adminRepository.findAll().stream()
                .map(AdminEntityMapper::toDomainObject)
                .collect(Collectors.toList());
    }
}