package com.example.demo.category.controller;

import com.example.demo.category.dto.CreateCategoryRequest;
import com.example.demo.category.entity.Category;
import com.example.demo.category.service.CategoryService;
import com.example.demo.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * GET /api/v1/categories
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> index(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        Page<Category> categoryPage = categoryService.getCategories(page, limit);

        return ApiResponse.<List<Category>>builder()
                .success("Kateqoriyalar uğurla gətirildi")
                .setPaginatedData(categoryPage.getContent(), categoryPage)
                .status(HttpStatus.OK)
                .response();
    }

    /**
     * POST /api/v1/categories
     * Handles multipart/form-data for file uploads (icon) and DTO validation.
     */
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<ApiResponse<Category>> store(@Valid @ModelAttribute CreateCategoryRequest request) {
        Category category = categoryService.createCategory(request);
        return ApiResponse.created("Kateqoriya uğurla yaradıldı", category);
    }
}