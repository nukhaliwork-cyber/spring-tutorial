package com.example.demo.category.entity;

import com.example.demo.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "categories")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity {

    @Column(name="title", nullable = false, length = 150)
    private String title;

    @Column(name="slug", nullable = false, unique = true, length = 180)
    private String slug;

    @Column(name="description", nullable = true, columnDefinition = "TEXT")
    private String description;

    @Column(name="icon", nullable = true, length = 180)
    private String icon;

    @Column(name = "parent_id", nullable = true)
    private Long parentId;

    @Builder.Default
    @Column(name = "rank")
    private Integer rank = 0;
}