package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence;

import com.semicolon.ecommerceTask.application.port.output.persistence.ProductPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.ProductDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.ProductEntity;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.ProductPersistenceMapper;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements ProductPersistenceOutPort {

    private final ProductRepository productRepository;
    private final ProductPersistenceMapper mapper;

    @Override
    public ProductDomainObject saveProduct(ProductDomainObject product) {
        ProductEntity entity = mapper.toEntity(product);
        ProductEntity savedEntity = productRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<ProductDomainObject> findProductById(UUID productId) {
        return productRepository.findById(productId)
                .map(mapper::toDomain);
    }

    @Override
    public List<ProductDomainObject> findAllProductsBySellerId(UUID sellerId) {
        List<ProductEntity> entities = productRepository.findAllBySellerId(sellerId);
        return mapper.toDomainList(entities);
    }

    @Override
    public void deleteProductById(UUID productId) {
        productRepository.deleteById(productId);
    }
}