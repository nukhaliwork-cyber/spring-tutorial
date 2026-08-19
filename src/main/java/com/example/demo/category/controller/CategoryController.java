package com.example.demo.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import com.example.demo.category.entity.Category;
import com.example.demo.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import com.example.demo.category.service.CategoryService;


@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> index(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int limit
    )
    {
        Page<Category> categoryPage = categoryService.getCategories(page, limit);


        return ApiResponse.<List<Category>>builder()
        .success("Kateqoriyalar uğurla gətirildi")
        .setPaginatedData(categoryPage.getContent(), categoryPage)
        .status(HttpStatus.OK)
        .response();
    }

}