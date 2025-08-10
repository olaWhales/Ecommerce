package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence;

import com.semicolon.ecommerceTask.application.port.output.persistence.AdminPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.AdminDomainObject;
import com.semicolon.ecommerceTask.domain.model.PendingRegistration;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.AdminEntity;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.PendingRegistrationEntity;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.AdminMapper;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.repository.AdminRepository;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.repository.PendingRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AdminPersistenceAdapter implements AdminPersistenceOutPort {

    private final AdminRepository adminRepository;
    private final PendingRegistrationRepository pendingRegistrationRepository;
    private final AdminMapper adminMapper;

    @Override
    public boolean existsByEmail(String email) {
        return adminRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public void savePendingRegistration(String email, String token, LocalDateTime expiration) {
        // Check if a pending registration already exists
        Optional<PendingRegistrationEntity> existing = pendingRegistrationRepository.findById(email);
        PendingRegistrationEntity entity = adminMapper.toPendingEntity(email, token, expiration);

        if (existing.isPresent()) {
            // Update existing entity
            PendingRegistrationEntity existingEntity = existing.get();
            existingEntity.setToken(token);
            existingEntity.setExpiration(expiration);
            pendingRegistrationRepository.save(existingEntity);
        } else {
            // Save new entity
            pendingRegistrationRepository.save(entity);
        }
    }

    @Override
    public Optional<PendingRegistration> findPendingTokenByEmail(String email) {
        return pendingRegistrationRepository.findById(email)
                .map(pe -> new PendingRegistration(pe.getEmail(), pe.getToken(), pe.getExpiration()));
    }

    @Override
    @Transactional
    public void deletePendingRegistration(String email) {
        pendingRegistrationRepository.deleteById(email);
    }

    @Override
    @Transactional
    public void saveAdmin(AdminDomainObject admin) {
        AdminEntity entity = adminMapper.toEntity(admin);
        adminRepository.save(entity);
    }

    @Override
    public Optional<AdminDomainObject> findByEmail(String email) {
        return adminRepository.findByEmail(email)
                .map(adminMapper::toDomainObject);
    }

    @Override
    @Transactional
    public void deleteAdmin(String email) {
        adminRepository.deleteByEmail(email);
    }

    @Override
    public List<AdminDomainObject> findAllAdmins() {
        return adminMapper.toDomainObjectList(adminRepository.findAll());
    }
}