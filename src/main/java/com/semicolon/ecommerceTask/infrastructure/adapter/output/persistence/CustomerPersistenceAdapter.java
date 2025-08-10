package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence;

import com.semicolon.ecommerceTask.application.port.output.persistence.CustomerPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.CustomerDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.CustomerMapper;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerPersistenceAdapter implements CustomerPersistenceOutPort {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public void saveCustomer(CustomerDomainObject customer) {
        // Map the domain object to a database entity
        var customerEntity = customerMapper.toEntity(customer);

        // Save the entity using the JPA repository
        customerRepository.save(customerEntity);
    }
}