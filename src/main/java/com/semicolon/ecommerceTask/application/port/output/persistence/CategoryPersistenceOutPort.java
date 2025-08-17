package com.semicolon.ecommerceTask.application.port.output.persistence;

import com.semicolon.ecommerceTask.domain.model.CategoryDomainObject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryPersistenceOutPort {
    CategoryDomainObject save(CategoryDomainObject categoryDomainObject);
    Optional<CategoryDomainObject> findById(UUID id);
    List<CategoryDomainObject> findAll();
    boolean existsByName(String name);
    void validateCategoryExists(String name);
}
