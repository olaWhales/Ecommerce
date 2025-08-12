package com.semicolon.ecommerceTask.application.port.output;

import com.semicolon.ecommerceTask.domain.model.PendingRegistrationDomainObject;

import java.util.Optional;
import java.util.UUID;

public interface PendingRegistrationOutPort {
    Optional<PendingRegistrationDomainObject> findById(UUID registrationId);
    void delete(UUID registrationId);
}
