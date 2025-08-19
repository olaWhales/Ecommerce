package com.semicolon.ecommerceTask.application.port.output.persistence;

import com.semicolon.ecommerceTask.domain.model.ManageProductDomainObject;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductPersistenceOutPort {
    Optional<ManageProductDomainObject> findById(UUID id);
    ManageProductDomainObject save(ManageProductDomainObject domain);
    void deleteById(UUID id);

    Collection<ManageProductDomainObject> findAll();

    List<ManageProductDomainObject> findAllBySellerId(String sellerId);
}