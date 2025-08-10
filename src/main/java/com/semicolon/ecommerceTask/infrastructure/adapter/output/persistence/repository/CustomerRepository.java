package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.repository;

import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}