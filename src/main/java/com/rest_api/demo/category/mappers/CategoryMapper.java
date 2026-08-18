package com.rest_api.demo.category.mappers;

import com.rest_api.demo.category.dto.CategoryResponse;
import com.rest_api.demo.category.dto.CreateCategoryRequest;
import com.rest_api.demo.category.dto.UpdateCategoryRequest;
import com.rest_api.demo.category.entities.Category;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public CategoryResponse toResponse(Category entity) {
        if (entity == null) {
            return null;
        }

        List<CategoryResponse> childrenResponses = new ArrayList<>();
        if (entity.getChildren() != null && !entity.getChildren().isEmpty()) {
            childrenResponses = entity.getChildren().stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());
        }

        return CategoryResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .parentId(entity.getParent() != null ? entity.getParent().getId() : null)
                .rank(entity.getRank())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .children(childrenResponses)
                .build();
    }

    public Category toEntity(CreateCategoryRequest request, Category parent) {
        return Category.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .rank(request.getRank() != null ? request.getRank() : 0)
                .parent(parent)
                .children(new ArrayList<>())
                .build();
    }

    public void updateEntityFromRequest(Category entity, UpdateCategoryRequest request, Category parent) {
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setParent(parent);
        if (request.getRank() != null) {
            entity.setRank(request.getRank());
        }
    }
}
