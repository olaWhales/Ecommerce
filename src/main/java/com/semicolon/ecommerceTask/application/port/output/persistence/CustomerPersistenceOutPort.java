package com.semicolon.ecommerceTask.application.port.output.persistence;

import com.semicolon.ecommerceTask.domain.model.CustomerDomainObject;

public interface CustomerPersistenceOutPort {
    void saveCustomer(CustomerDomainObject customer);
    // You can add more methods here like find, delete, etc.
}