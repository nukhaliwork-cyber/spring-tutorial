package com.rest_api.demo.category.entities;

import com.rest_api.demo.common.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    @Column(nullable = false, length = 150)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "parent_id")
    private Long parentId;

    @Builder.Default
    @Column(nullable = false)
    private Integer rank = 0;
}
