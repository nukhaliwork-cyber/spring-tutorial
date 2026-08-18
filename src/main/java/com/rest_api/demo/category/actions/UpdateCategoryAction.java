package com.rest_api.demo.category.actions;

import com.rest_api.demo.category.dto.CategoryResponse;
import com.rest_api.demo.category.dto.UpdateCategoryRequest;
import com.rest_api.demo.category.entities.Category;
import com.rest_api.demo.category.mappers.CategoryMapper;
import com.rest_api.demo.category.repositories.CategoryRepository;
import com.rest_api.demo.common.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateCategoryAction {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryResponse execute(Long id, UpdateCategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID " + id + " olan kateqoriya tapılmadı"));

        categoryMapper.updateEntityFromRequest(category, request);
        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.toResponse(updatedCategory);
    }
}
