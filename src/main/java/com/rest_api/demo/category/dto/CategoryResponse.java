package com.rest_api.demo.category.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CategoryResponse {
    private Long id;
    private String title;
    private String description;
    private Long parentId;
    private Integer rank;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CategoryResponse> children = new ArrayList<>();

    public CategoryResponse() {
    }

    public CategoryResponse(Long id, String title, String description, Long parentId, Integer rank, LocalDateTime createdAt, LocalDateTime updatedAt, List<CategoryResponse> children) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.parentId = parentId;
        this.rank = rank;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.children = children != null ? children : new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<CategoryResponse> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryResponse> children) {
        this.children = children;
    }
}
