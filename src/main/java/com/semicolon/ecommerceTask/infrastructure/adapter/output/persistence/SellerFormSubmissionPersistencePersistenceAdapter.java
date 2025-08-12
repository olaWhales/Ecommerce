package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence;

import com.semicolon.ecommerceTask.application.port.output.persistence.SellerFormSubmissionPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.SellerFormSubmissionDomain;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.userEntity.SellerFormSubmissionEntity;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.SellerFormSubmissionMapper;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.repository.SellerFormSubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SellerFormSubmissionPersistencePersistenceAdapter implements SellerFormSubmissionPersistenceOutPort {
    private final SellerFormSubmissionRepository sellerFormSubmissionRepository;
    private final SellerFormSubmissionMapper sellerFormSubmissionMapper;
    @Override
    public SellerFormSubmissionDomain savePendingRegistration(SellerFormSubmissionDomain registration) {
        SellerFormSubmissionEntity entity = sellerFormSubmissionMapper.toEntity(registration);
        SellerFormSubmissionEntity savedEntity = sellerFormSubmissionRepository.save(entity);
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