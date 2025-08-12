package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence;

import com.semicolon.ecommerceTask.application.port.input.SellerFormSubmissionOutPort;
import com.semicolon.ecommerceTask.domain.model.SellerFormSubmissionDomain;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.SellerFormSubmissionEntity;
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
public class SellerFormSubmissionPersistenceAdapter implements SellerFormSubmissionOutPort {
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
//    </SellerFormSubmissionDomain>
    @Override
    public Optional<SellerFormSubmissionDomain> findById(UUID id) {
        return sellerFormSubmissionRepository.findById(id)
                .map(sellerFormSubmissionMapper::toDomainObject);
    }
//    </SellerFormSubmissionDomain>
    @Override
    public List<SellerFormSubmissionDomain> findAllPendingRegistrations() {
        return sellerFormSubmissionRepository.findAll().stream()
                .map(sellerFormSubmissionMapper::toDomainObject)
                .collect(Collectors.toList());
    }
//    </SellerFormSubmissionDomain>
    @Override
    public void deletePendingRegistration(String email) {
        sellerFormSubmissionRepository.findByCustomerEmail(email)
                .ifPresent(sellerFormSubmissionRepository::delete);
    }
}