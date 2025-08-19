package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence;

import com.semicolon.ecommerceTask.application.port.output.persistence.ProductPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.ManageProductDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entities.ProductEntity;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.productManagentsMapper.ProductPersistenceMapper;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.repositories.ManageProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ManageProductPersistenceAdapter implements ProductPersistenceOutPort {

    private final ManageProductRepository productRepository;
    private final ProductPersistenceMapper mapper;

    @Override
    public Optional<ManageProductDomainObject> findById(UUID id) {
        return productRepository.findById(id).map(mapper::toProductDomain);
    }

    @Override
    public ManageProductDomainObject save(ManageProductDomainObject domain) {
        ProductEntity entity = mapper.toProductEntity(domain);
        ProductEntity save = productRepository.save(entity);
        return mapper.toProductDomain(save);
    }

    @Override
    public void deleteById(UUID id) {
        productRepository.deleteById(id);
    }

    @Override
    public Collection<ManageProductDomainObject> findAll() {
        return productRepository.findAll().stream().map(mapper::toProductDomain).toList();
    }

    @Override
    public List<ManageProductDomainObject> findAllBySellerId(String sellerId) {
        return productRepository.findAllBySellerId(sellerId).stream().map(mapper::toProductDomain).toList();
    }
}