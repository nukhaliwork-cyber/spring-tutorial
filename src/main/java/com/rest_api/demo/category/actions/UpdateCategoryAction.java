package com.rest_api.demo.category.actions;

import com.rest_api.demo.category.dto.CategoryResponse;
import com.rest_api.demo.category.dto.UpdateCategoryRequest;
import com.rest_api.demo.category.entities.Category;
import com.rest_api.demo.category.mappers.CategoryMapper;
import com.rest_api.demo.category.repositories.CategoryRepository;
import com.rest_api.demo.common.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UpdateCategoryAction {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public UpdateCategoryAction(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public CategoryResponse execute(Long id, UpdateCategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID " + id + " olan kateqoriya tapılmadı"));

        Category parent = null;
        if (request.getParentId() != null && request.getParentId() > 0) {
            if (request.getParentId().equals(id)) {
                throw new IllegalArgumentException("Kateqoriya özünün valideyni ola bilməz");
            }
            parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Valideyn kateqoriya tapılmadı: ID " + request.getParentId()));
        }

        categoryMapper.updateEntityFromRequest(category, request, parent);
        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.toResponse(updatedCategory);
    }
}
