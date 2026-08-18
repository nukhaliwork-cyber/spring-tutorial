package com.rest_api.demo.category.controllers;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @GetMapping
  public List<Map<String, Object>> getAllUsers() {
        return List.of(
            Map.of("id", 1, "name", "Ali", "app", appName),
            Map.of("id", 2, "name", "Murad", "app", appName)
        );
    }

}