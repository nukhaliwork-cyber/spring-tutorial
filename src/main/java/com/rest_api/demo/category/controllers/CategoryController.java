package com.rest_api.demo.category.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Value("${spring.application.name:demo}")
    private String appName;

    @GetMapping
    public List<Map<String, Object>> getAllCategories() {
        return List.of(
            Map.of("id", 1, "name", "Texnologiya", "app", appName),
            Map.of("id", 2, "name", "Dizayn", "app", appName)
        );
    }

}