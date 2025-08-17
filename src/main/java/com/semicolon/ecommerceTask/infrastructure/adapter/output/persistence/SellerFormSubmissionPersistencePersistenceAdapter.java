package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence;

import com.semicolon.ecommerceTask.application.port.output.persistence.SellerFormSubmissionPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.SellerFormSubmissionDomain;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.temporaryPendingRegistrationEntity.SellerFormSubmissionEntity;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.SellerFormSubmissionMapper;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.repository.SellerFormSubmissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class SellerFormSubmissionPersistencePersistenceAdapter implements SellerFormSubmissionPersistenceOutPort {
    private final SellerFormSubmissionRepository sellerFormSubmissionRepository;
    private final SellerFormSubmissionMapper sellerFormSubmissionMapper;

    @Override
    public Optional<SellerFormSubmissionDomain> findByKeycloakUserId(String keycloakUserId) {
        return sellerFormSubmissionRepository.findByKeycloakUserId(keycloakUserId)
                .map(sellerFormSubmissionMapper::toDomainObject);
    }

    @Override
    public SellerFormSubmissionDomain savePendingRegistration(SellerFormSubmissionDomain registration) {
        log.info("Saving registration: id={}, email={}, keycloakUserId={}",
                registration.getId(), registration.getCustomerEmail(), registration.getKeycloakUserId());
        SellerFormSubmissionEntity entity = sellerFormSubmissionMapper.toEntity(registration);
        log.info("Mapped entity: id={}, email={}, keycloakUserId={}",
                entity.getId(), entity.getCustomerEmail(), entity.getKeycloakUserId());
        SellerFormSubmissionEntity savedEntity = sellerFormSubmissionRepository.save(entity);
        log.info("Saved entity: id={}, email={}, keycloakUserId={}",
                savedEntity.getId(), savedEntity.getCustomerEmail(), savedEntity.getKeycloakUserId());
        return sellerFormSubmissionMapper.toDomainObject(savedEntity);
    }

    @Override
    public Optional<SellerFormSubmissionDomain> findByEmail(String email) {
        return sellerFormSubmissionRepository.findByCustomerEmail(email)
                .map(sellerFormSubmissionMapper::toDomainObject);
    }

    @Override
    public Optional<SellerFormSubmissionDomain> findById(UUID id) {
        return sellerFormSubmissionRepository.findById(id)
                .map(sellerFormSubmissionMapper::toDomainObject);
    }
    @Override
    public List<SellerFormSubmissionDomain> findAllPendingRegistrations() {
        return sellerFormSubmissionRepository.findAll().stream()
                .map(sellerFormSubmissionMapper::toDomainObject)
                .collect(Collectors.toList());
    }

    @Override
    public SellerFormSubmissionDomain save(SellerFormSubmissionDomain submission) {
        SellerFormSubmissionEntity entity = sellerFormSubmissionMapper.toEntity(submission);
        return sellerFormSubmissionMapper.toDomainObject(sellerFormSubmissionRepository.save(entity));
    }

    @Override
    public void deletePendingRegistration(String email) {
        sellerFormSubmissionRepository.findByCustomerEmail(email)
                .ifPresent(sellerFormSubmissionRepository::delete);
    }
}