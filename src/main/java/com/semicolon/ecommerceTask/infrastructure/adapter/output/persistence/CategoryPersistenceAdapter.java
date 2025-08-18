package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence;

import com.semicolon.ecommerceTask.application.port.output.persistence.CategoryPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.exception.ValidationException;
import com.semicolon.ecommerceTask.domain.model.CategoryDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entities.CategoryEntity;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.CategoryMapper;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.repositories.CategoryRepository;
import com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryPersistenceAdapter implements CategoryPersistenceOutPort {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDomainObject save(CategoryDomainObject categoryDomainObject) {
        CategoryEntity entity = categoryMapper.toCategoryEntity(categoryDomainObject);
        CategoryEntity savedEntity = categoryRepository.save(entity);
        return categoryMapper.toCategoryDomain(savedEntity);
    }

    @Override
    public Optional<CategoryDomainObject> findById(UUID id) {
        return categoryRepository.findById(id).map(categoryMapper::toCategoryDomain);
    }

    @Override
    public List<CategoryDomainObject> findAll() {
        return categoryRepository.findAll().stream()
            .map(categoryMapper::toCategoryDomain)
            .collect(Collectors.toList());
    }

    @Override
    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    @Override
    public void validateCategoryExists(String name) {
        if (!categoryRepository.existsByName(name)) {
            throw new ValidationException(MessageUtil.CATEGORY_NOT_FOUND);
        }
    }
}