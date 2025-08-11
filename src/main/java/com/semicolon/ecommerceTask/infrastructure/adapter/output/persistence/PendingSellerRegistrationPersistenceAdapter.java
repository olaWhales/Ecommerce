package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence;

import com.semicolon.ecommerceTask.application.port.input.PendingSellerRegistrationOutPort;
import com.semicolon.ecommerceTask.domain.model.PendingSellerRegistration;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.PendingSellerRegistrationMapper;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.repository.PendingSellerRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PendingSellerRegistrationPersistenceAdapter implements PendingSellerRegistrationOutPort {

    private final PendingSellerRegistrationRepository repository;
    private final PendingSellerRegistrationMapper mapper;

    @Override
    public void savePendingRegistration(PendingSellerRegistration registration) {
        var entity = mapper.toEntity(registration);
        repository.save(entity);
    }

    @Override
    public Optional<PendingSellerRegistration> findByEmail(String email) {
        return repository.findByCustomerEmail(email)
                .map(mapper::toDomainObject);
    }

    @Override
    public Optional<PendingSellerRegistration> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<PendingSellerRegistration> findAllPendingRegistrations() {
        return mapper.toDomainObjectList(repository.findAll());
    }

    @Override
    public void deletePendingRegistration(String email) {
        repository.findByCustomerEmail(email)
                .ifPresent(repository::delete);
    }
}