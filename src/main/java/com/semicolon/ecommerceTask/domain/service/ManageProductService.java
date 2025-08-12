package com.semicolon.ecommerceTask.domain.service;

import com.semicolon.ecommerceTask.application.port.input.ManageProductUseCase;
import com.semicolon.ecommerceTask.application.port.output.FileStorageOutPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.ProductPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.ProductDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.ProductUploadDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.ProductUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ManageProductService implements ManageProductUseCase {

    private final ProductPersistenceOutPort productPersistenceOutPort;
    private final FileStorageOutPort fileStorageOutPort;

    @Override
    public ProductDomainObject createProduct(ProductUploadDto productDto, MultipartFile imageFile, UUID sellerId) {
        String imageUrl;
        try {
            imageUrl = fileStorageOutPort.uploadImage(imageFile);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
        ProductDomainObject product = ProductDomainObject.builder()
            .name(productDto.getName())
            .description(productDto.getDescription())
            .price(productDto.getPrice())
            .inStockQuantity(productDto.getInStockQuantity())
            .sellerId(sellerId)
            .imageUrl(imageUrl)
            .build();
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

        ProductDomainObject updatedProduct = existingProduct.toBuilder()
                .name(productDto.getName() != null ? productDto.getName() : existingProduct.getName())
                .description(productDto.getDescription() != null ? productDto.getDescription() : existingProduct.getDescription())
                .price(productDto.getPrice() != null ? productDto.getPrice() : existingProduct.getPrice())
                .inStockQuantity(productDto.getInStockQuantity() > 0 ? productDto.getInStockQuantity() : existingProduct.getInStockQuantity())
                .imageUrl(productDto.getImageUrl() != null ? productDto.getImageUrl() : existingProduct.getImageUrl())
                .build();

        return productPersistenceOutPort.saveProduct(updatedProduct);
    }

    @Override
    public void deleteProduct(UUID productId) {
        productPersistenceOutPort.deleteProductById(productId);
    }
}
