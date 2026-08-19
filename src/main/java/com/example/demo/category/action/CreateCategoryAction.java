package com.example.demo.category.action;

import com.example.demo.category.dto.CreateCategoryRequest;
import com.example.demo.category.entity.Category;
import com.example.demo.category.repository.CategoryRepository;
import com.example.demo.common.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateCategoryAction {

    private final CategoryRepository categoryRepository;
    private final FileUploadService fileUploadService;

    public Category execute(CreateCategoryRequest request) {
        // 1. Upload icon if provided
        String iconPath = fileUploadService.uploadFile(request.getIcon(), "categories");

        // 2. Map DTO to Entity and save
        Category category = Category.builder()
                .title(request.getTitle())
                .slug(request.getSlug())
                .description(request.getDescription())
                .icon(iconPath)
                .parentId(request.getParentId())
                .rank(request.getRank() != null ? request.getRank() : 0)
                .build();

        return categoryRepository.save(category);
    }
}
