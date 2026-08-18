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

        return new CategoryResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getParent() != null ? entity.getParent().getId() : null,
                entity.getRank(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                childrenResponses
        );
    }

    public Category toEntity(CreateCategoryRequest request, Category parent) {
        return new Category(
                request.getTitle(),
                request.getDescription(),
                request.getRank() != null ? request.getRank() : 0,
                parent
        );
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
