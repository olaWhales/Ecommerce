package com.semicolon.ecommerceTask.application.port.output;

import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.AdminEntity;

import java.util.Optional;
import java.util.UUID;

public interface PendingRegistrationOutPort {
    Optional<AdminEntity.PendingRegistrationDomainObject> findById(UUID registrationId);
    void delete(UUID registrationId);
}
