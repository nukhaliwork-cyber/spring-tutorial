package com.rest_api.demo.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class CreateCategoryRequest {

    @NotBlank(message = "Kateqoriya başlığı mütləq daxil edilməlidir")
    @Size(min = 2, max = 150, message = "Başlıq 2 ilə 150 simvol arasında olmalıdır")
    private String title;

    private String description;

    private Long parentId;

    @NotNull(message = "Sıralama (rank) boş ola bilməz")
    @PositiveOrZero(message = "Rank 0 və ya müsbət ədəd olmalıdır")
    private Integer rank;

    public CreateCategoryRequest() {
    }

    public CreateCategoryRequest(String title, String description, Long parentId, Integer rank) {
        this.title = title;
        this.description = description;
        this.parentId = parentId;
        this.rank = rank;
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
}
