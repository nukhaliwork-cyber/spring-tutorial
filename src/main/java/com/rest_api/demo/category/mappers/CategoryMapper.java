package com.rest_api.demo.category.mappers;

import com.rest_api.demo.category.dto.CategoryResponse;
import com.rest_api.demo.category.dto.CreateCategoryRequest;
import com.rest_api.demo.category.dto.UpdateCategoryRequest;
import com.rest_api.demo.category.entities.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponse toResponse(Category entity) {
        if (entity == null) {
            return null;
        }
        return CategoryResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .parentId(entity.getParentId())
                .rank(entity.getRank())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public Category toEntity(CreateCategoryRequest request) {
        return Category.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .parentId(request.getParentId())
                .rank(request.getRank() != null ? request.getRank() : 0)
                .build();
    }

    public void updateEntityFromRequest(Category entity, UpdateCategoryRequest request) {
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setParentId(request.getParentId());
        if (request.getRank() != null) {
            entity.setRank(request.getRank());
        }
    }
}
