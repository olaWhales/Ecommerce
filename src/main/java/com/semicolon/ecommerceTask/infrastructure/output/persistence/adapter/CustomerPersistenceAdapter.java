package com.semicolon.ecommerceTask.infrastructure.output.persistence.adapter;

import com.semicolon.ecommerceTask.application.port.output.persistence.CustomerPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.CustomerDomainObject;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.persistenceEntities.UserEntity;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.mapper.CustomerMapper;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.repositories.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerPersistenceAdapter implements CustomerPersistenceOutPort {
    private final JpaUserRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public void saveCustomer(CustomerDomainObject customer) {
        UserEntity customerEntity = customerMapper.toEntity(customer);
        customerRepository.save(customerEntity);
    }
}

