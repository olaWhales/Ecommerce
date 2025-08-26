package com.semicolon.ecommerceTask.infrastructure.controllers;

import com.semicolon.ecommerceTask.application.port.input.CategoryUseCase;
import com.semicolon.ecommerceTask.domain.model.CategoryDomainObject;
import com.semicolon.ecommerceTask.infrastructure.input.data.requests.CategoryCreationDto;
import com.semicolon.ecommerceTask.infrastructure.input.data.responses.CategoryRegResponse;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.mapper.CategoryMapper;
import com.semicolon.ecommerceTask.infrastructure.utilities.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryUseCase categoryUseCase;
    private final CategoryMapper categoryMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryRegResponse> createCategory(@RequestBody CategoryCreationDto dto) {
        CategoryDomainObject domain = categoryMapper.toCategoryDomain(dto);
        CategoryDomainObject savedDomain = categoryUseCase.createCategory(domain);
        CategoryRegResponse response = categoryMapper.toCategoryResponseDto(savedDomain);
        response.setMessage(MessageUtil.CATEGORY_CREATED_SUCCESSFULLY);
        return ResponseEntity.created(URI.create("/api/categories/" + savedDomain.getId())).body(response);
    }
}