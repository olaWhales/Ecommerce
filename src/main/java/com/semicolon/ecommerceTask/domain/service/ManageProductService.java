package com.semicolon.ecommerceTask.domain.service;

import com.semicolon.ecommerceTask.application.port.input.ManageProductUseCase;
import com.semicolon.ecommerceTask.application.port.output.FileStorageOutPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.ProductPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.ProductDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.ProductUploadDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.ProductUpdateDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.ProductPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ManageProductService implements ManageProductUseCase {

    private final ProductPersistenceOutPort productPersistenceOutPort;
    private final FileStorageOutPort fileStorageOutPort;
    private final ProductPersistenceMapper productDtoMapper;

    @Override
    public ProductDomainObject createProduct(ProductUploadDto productDto, UUID sellerId) {
        String imageUrl;
        try {
            imageUrl = fileStorageOutPort.uploadImage(productDto.getImageFile());
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }

        ProductDomainObject product = productDtoMapper.toDomain(productDto);
        product.setSellerId(sellerId);
        product.setImageUrl(imageUrl);
        return productPersistenceOutPort.saveProduct(product);
    }

    @Override
    public ProductDomainObject getProduct(UUID productId) {
        return productPersistenceOutPort.findProductById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public List<ProductDomainObject> getAllProductsBySeller(UUID sellerId) {
        return productPersistenceOutPort.findAllProductsBySellerId(sellerId);
    }

    @Override
    public ProductDomainObject updateProduct(UUID productId, ProductUpdateDto productDto) {
        ProductDomainObject existingProduct = getProduct(productId);
        productDtoMapper.updateDomainFromDto(productDto, existingProduct);
        return productPersistenceOutPort.saveProduct(existingProduct);
    }

    @Override
    public void deleteProduct(UUID productId) {
        productPersistenceOutPort.deleteProductById(productId);
    }
}
