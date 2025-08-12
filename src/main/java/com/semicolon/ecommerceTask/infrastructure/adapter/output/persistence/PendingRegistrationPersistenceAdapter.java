package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence;

import com.semicolon.ecommerceTask.application.port.output.PendingRegistrationOutPort;
import com.semicolon.ecommerceTask.domain.model.PendingRegistrationDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.PendingRegistrationMapper;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.repository.PendingRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PendingRegistrationPersistenceAdapter implements PendingRegistrationOutPort {

    private final PendingRegistrationRepository repository;
    private final PendingRegistrationMapper mapper;

    @Override
    public Optional<PendingRegistrationDomainObject> findById(UUID registrationId) {
        return repository.findById(registrationId)
                .map(mapper::toDomain);
    }

    @Override
    public void delete(UUID registrationId) {
        repository.deleteById(registrationId);
    }
}