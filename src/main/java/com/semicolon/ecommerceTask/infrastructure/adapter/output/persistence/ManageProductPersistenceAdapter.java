package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence;

import com.semicolon.ecommerceTask.application.port.output.persistence.ProductPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.ManageProductDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.manageProductDto.ProductUploadDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.ProductEntity;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.ProductPersistenceMapper;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.repository.ManageProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ManageProductPersistenceAdapter implements ProductPersistenceOutPort {

    private final ManageProductRepository productRepository;
    private final ProductPersistenceMapper mapper;

    @Override
    public Optional<ManageProductDomainObject> findById(UUID id) {
        return productRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public ManageProductDomainObject save(ManageProductDomainObject product) {
        return null;
    }

    public ManageProductDomainObject save(ProductUploadDto product) {
        ProductEntity entity = mapper.toEntity(product);
        return mapper.toDomain(productRepository.save(entity));
    }

    @Override
    public void deleteById(UUID id) {
        productRepository.deleteById(id);
    }
}