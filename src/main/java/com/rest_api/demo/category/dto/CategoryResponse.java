package com.rest_api.demo.category.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {
    private Long id;
    private String title;
    private String description;
    private Long parentId;
    private Integer rank;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
